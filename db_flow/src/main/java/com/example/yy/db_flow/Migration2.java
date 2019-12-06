package com.example.yy.db_flow;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

@Migration(version = 2,database = MyDatabase.class)
public class Migration2 extends AlterTableMigration<Student> {
    public Migration2(Class<Student> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        addColumn(SQLiteType.INTEGER,"mom_id");
    }
}
