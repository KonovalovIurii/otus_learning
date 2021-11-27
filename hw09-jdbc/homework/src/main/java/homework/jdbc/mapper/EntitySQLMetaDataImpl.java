package homework.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData entityClassMetaData;
    private final String fieldsList;
    private final String fieldsListWoId;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        List<Field> fields = entityClassMetaData.getAllFields();
        // преобразуем полученные поля в строку значений
        this.fieldsList = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        // получим поля без ID
        List<Field> fieldsWoId = entityClassMetaData.getFieldsWithoutId();
        // преобразуем полученные поля в строку значений
        this.fieldsListWoId = fieldsWoId.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String getSelectAllSql() {
        return "select " + fieldsList + " from " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "select " + fieldsList + " from " + entityClassMetaData.getName() + " where " + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        return "insert into " + entityClassMetaData.getName() + " (" + fieldsListWoId + ") " + " values(" + fieldsListWoId.replaceAll("[^,]", "").replace(",", "?,").concat("?") + ")";
    }

    @Override
    public String getUpdateSql() {
        // получим все поля
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        // преобразуем полученные поля в строку значений
        String fieldsList = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining("=?, "));
        fieldsList = fieldsList.concat("=?");
        return "update "+ entityClassMetaData.getName() +" set " + fieldsList + " where " + entityClassMetaData.getIdField().getName() + " = ?";
    }
}
