import model.entity.User;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class HibernateRunnerTest {

    @Test
    void checkReflectionApi() {
        User user = User.builder()
                .build();

        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;

        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();

        String columnNames = Arrays.stream(declaredFields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(", "));

        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));
    }

    @Test
    public void test2() {

        String maxServiceVersionByDescriptor = "5.9";
        BigDecimal maxServiceVersion = new BigDecimal(maxServiceVersionByDescriptor);
        BigDecimal newVersion = maxServiceVersion.add(BigDecimal.ONE).setScale(0, RoundingMode.DOWN);

        System.out.println(increaseFractionalPart(maxServiceVersionByDescriptor));

//            BigDecimal baseServiceVersion = new BigDecimal(baseService.getVersion());
//            newVersion = baseServiceVersion.add(BigDecimal.valueOf(0.1));

    }

    @Test
    public void createNewVersion() {
        Stream.of("1.0", "2.0", "2.1", "3.9", "4.3", "5.9", "6.8")
                .forEach(lv -> {
                    String nv = getNewServiceVersion2(lv);
                    System.out.println("---------------");
                    System.out.println("last vesrion = " + lv);
                    System.out.println("new vesrion = " + nv);
                });
    }

    private String getNewServiceVersion(String parentServiceVersion) {
        if (parentServiceVersion == null) {
            return null;
//            throw new InternalException("Не возможно рассчитать новую версию сервиса, parentServiceVersion = null");
        }

        String[] numberParts = parentServiceVersion.split("\\.");

        int wholePart;
        int fractionalPart;
        try {
            wholePart = Integer.parseInt(numberParts[0]);
            fractionalPart = Integer.parseInt(numberParts[1]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка парсинга дробной части версии, версия = %s, дробная часть = %s".formatted(parentServiceVersion, parentServiceVersion));
            return null;
        }

        int newFractionalPart = fractionalPart != 9 ? fractionalPart + 1 : 0;
        int newWholePart = fractionalPart != 9 ? wholePart : wholePart + 1;

        return newWholePart + "." + newFractionalPart;
    }

    private String increaseFractionalPart(String parentServiceVersion) {
        if (parentServiceVersion == null) {
            return null;
//            throw new InternalException("Не возможно рассчитать новую версию сервиса, parentServiceVersion = null");
        }

        String[] numberParts = parentServiceVersion.split("\\.");

        String wholePart = numberParts[0];
        int fractionalPart;
        try {
            fractionalPart = Integer.parseInt(numberParts[1]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка парсинга дробной части версии, версия = %s, дробная часть = %s".formatted(parentServiceVersion, parentServiceVersion));
            return null;
        }

        int newFractionalPart = fractionalPart + 1;

        return wholePart + "." + newFractionalPart;
    }

    private String getNewServiceVersion2(String parentServiceVersion) {
        if (parentServiceVersion == null) {
            return null;
//            throw new InternalException("Не возможно рассчитать новую версию сервиса, parentServiceVersion = null");
        }

        return new BigDecimal(parentServiceVersion).add(BigDecimal.valueOf(0.1D)).toString();
    }



  /*  private String getNewServiceVersion(String parentServiceVersion) {
        if (parentServiceVersion == null) {
            log.error("Не возможно рассчитать новую версию сервиса, parentServiceVersion = null");
            throw new InternalException("Не возможно рассчитать новую версию сервиса, parentServiceVersion = null");
        }

        String[] numberParts = parentServiceVersion.split("\\.");

        int wholePart;
        int fractionalPart;
        try {
            wholePart = Integer.parseInt(numberParts[0]);
            fractionalPart = Integer.parseInt(numberParts[1]);
        } catch (NumberFormatException e) {
            log.error("Не возможно рассчитать новую версию сервиса, parentServiceVersion = null");
            throw new InternalException("Ошибка парсинга дробной части версии, версия = %s, дробная часть = %s"
                    .formatted(parentServiceVersion, parentServiceVersion));
        }

        int newFractionalPart = fractionalPart != 9 ? fractionalPart + 1 : 0;
        int newWholePart = fractionalPart != 9 ? wholePart : wholePart + 1;

        return newWholePart + "." + newFractionalPart;
    }*/
}