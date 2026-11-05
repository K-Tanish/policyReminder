package com.policyreminder.app.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.policyreminder.app.data.dao.CallLogDao;
import com.policyreminder.app.data.dao.CallLogDao_Impl;
import com.policyreminder.app.data.dao.CustomerDao;
import com.policyreminder.app.data.dao.CustomerDao_Impl;
import com.policyreminder.app.data.dao.FamilyMemberDao;
import com.policyreminder.app.data.dao.FamilyMemberDao_Impl;
import com.policyreminder.app.data.dao.MessageTemplateDao;
import com.policyreminder.app.data.dao.MessageTemplateDao_Impl;
import com.policyreminder.app.data.dao.PolicyDao;
import com.policyreminder.app.data.dao.PolicyDao_Impl;
import com.policyreminder.app.data.dao.ReminderDao;
import com.policyreminder.app.data.dao.ReminderDao_Impl;
import com.policyreminder.app.data.dao.RenewalHistoryDao;
import com.policyreminder.app.data.dao.RenewalHistoryDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile CustomerDao _customerDao;

  private volatile FamilyMemberDao _familyMemberDao;

  private volatile PolicyDao _policyDao;

  private volatile ReminderDao _reminderDao;

  private volatile CallLogDao _callLogDao;

  private volatile MessageTemplateDao _messageTemplateDao;

  private volatile RenewalHistoryDao _renewalHistoryDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `customers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `firstName` TEXT NOT NULL, `middleName` TEXT NOT NULL, `surname` TEXT NOT NULL, `mobileNumber` TEXT NOT NULL, `alternateMobile` TEXT NOT NULL, `email` TEXT NOT NULL, `address` TEXT NOT NULL, `dateOfBirth` INTEGER, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `family_members` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `customerId` INTEGER NOT NULL, `name` TEXT NOT NULL, `relationship` TEXT NOT NULL, `dateOfBirth` INTEGER, `anniversaryDate` INTEGER, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`customerId`) REFERENCES `customers`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_family_members_customerId` ON `family_members` (`customerId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `policies` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `customerId` INTEGER NOT NULL, `policyNumber` TEXT NOT NULL, `insuranceCompany` TEXT NOT NULL, `policyType` TEXT NOT NULL, `policyholderName` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `renewalDate` INTEGER NOT NULL, `premiumAmount` REAL NOT NULL, `paymentFrequency` TEXT NOT NULL, `status` TEXT NOT NULL, `notes` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, FOREIGN KEY(`customerId`) REFERENCES `customers`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_policies_customerId` ON `policies` (`customerId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reminders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `customerId` INTEGER NOT NULL, `policyId` INTEGER, `familyMemberId` INTEGER, `type` TEXT NOT NULL, `reminderDate` INTEGER NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, `isSnoozed` INTEGER NOT NULL, `snoozeUntil` INTEGER, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `call_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `customerId` INTEGER NOT NULL, `policyId` INTEGER, `callDate` INTEGER NOT NULL, `outcome` TEXT NOT NULL, `followUpDate` INTEGER, `remarks` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`customerId`) REFERENCES `customers`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`policyId`) REFERENCES `policies`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_call_logs_customerId` ON `call_logs` (`customerId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_call_logs_policyId` ON `call_logs` (`policyId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `message_templates` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `content` TEXT NOT NULL, `isDefault` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `renewal_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `policyId` INTEGER NOT NULL, `previousRenewalDate` INTEGER NOT NULL, `newRenewalDate` INTEGER NOT NULL, `previousPremium` REAL NOT NULL, `newPremium` REAL NOT NULL, `remarks` TEXT NOT NULL, `renewedAt` INTEGER NOT NULL, FOREIGN KEY(`policyId`) REFERENCES `policies`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_renewal_history_policyId` ON `renewal_history` (`policyId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5d593f1903b195234441610e2f32df7d')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `customers`");
        db.execSQL("DROP TABLE IF EXISTS `family_members`");
        db.execSQL("DROP TABLE IF EXISTS `policies`");
        db.execSQL("DROP TABLE IF EXISTS `reminders`");
        db.execSQL("DROP TABLE IF EXISTS `call_logs`");
        db.execSQL("DROP TABLE IF EXISTS `message_templates`");
        db.execSQL("DROP TABLE IF EXISTS `renewal_history`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCustomers = new HashMap<String, TableInfo.Column>(11);
        _columnsCustomers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("firstName", new TableInfo.Column("firstName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("middleName", new TableInfo.Column("middleName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("surname", new TableInfo.Column("surname", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("mobileNumber", new TableInfo.Column("mobileNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("alternateMobile", new TableInfo.Column("alternateMobile", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("dateOfBirth", new TableInfo.Column("dateOfBirth", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCustomers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCustomers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCustomers = new TableInfo("customers", _columnsCustomers, _foreignKeysCustomers, _indicesCustomers);
        final TableInfo _existingCustomers = TableInfo.read(db, "customers");
        if (!_infoCustomers.equals(_existingCustomers)) {
          return new RoomOpenHelper.ValidationResult(false, "customers(com.policyreminder.app.data.model.Customer).\n"
                  + " Expected:\n" + _infoCustomers + "\n"
                  + " Found:\n" + _existingCustomers);
        }
        final HashMap<String, TableInfo.Column> _columnsFamilyMembers = new HashMap<String, TableInfo.Column>(7);
        _columnsFamilyMembers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFamilyMembers.put("customerId", new TableInfo.Column("customerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFamilyMembers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFamilyMembers.put("relationship", new TableInfo.Column("relationship", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFamilyMembers.put("dateOfBirth", new TableInfo.Column("dateOfBirth", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFamilyMembers.put("anniversaryDate", new TableInfo.Column("anniversaryDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFamilyMembers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFamilyMembers = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysFamilyMembers.add(new TableInfo.ForeignKey("customers", "CASCADE", "NO ACTION", Arrays.asList("customerId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesFamilyMembers = new HashSet<TableInfo.Index>(1);
        _indicesFamilyMembers.add(new TableInfo.Index("index_family_members_customerId", false, Arrays.asList("customerId"), Arrays.asList("ASC")));
        final TableInfo _infoFamilyMembers = new TableInfo("family_members", _columnsFamilyMembers, _foreignKeysFamilyMembers, _indicesFamilyMembers);
        final TableInfo _existingFamilyMembers = TableInfo.read(db, "family_members");
        if (!_infoFamilyMembers.equals(_existingFamilyMembers)) {
          return new RoomOpenHelper.ValidationResult(false, "family_members(com.policyreminder.app.data.model.FamilyMember).\n"
                  + " Expected:\n" + _infoFamilyMembers + "\n"
                  + " Found:\n" + _existingFamilyMembers);
        }
        final HashMap<String, TableInfo.Column> _columnsPolicies = new HashMap<String, TableInfo.Column>(14);
        _columnsPolicies.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("customerId", new TableInfo.Column("customerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("policyNumber", new TableInfo.Column("policyNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("insuranceCompany", new TableInfo.Column("insuranceCompany", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("policyType", new TableInfo.Column("policyType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("policyholderName", new TableInfo.Column("policyholderName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("renewalDate", new TableInfo.Column("renewalDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("premiumAmount", new TableInfo.Column("premiumAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("paymentFrequency", new TableInfo.Column("paymentFrequency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicies.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPolicies = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPolicies.add(new TableInfo.ForeignKey("customers", "CASCADE", "NO ACTION", Arrays.asList("customerId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesPolicies = new HashSet<TableInfo.Index>(1);
        _indicesPolicies.add(new TableInfo.Index("index_policies_customerId", false, Arrays.asList("customerId"), Arrays.asList("ASC")));
        final TableInfo _infoPolicies = new TableInfo("policies", _columnsPolicies, _foreignKeysPolicies, _indicesPolicies);
        final TableInfo _existingPolicies = TableInfo.read(db, "policies");
        if (!_infoPolicies.equals(_existingPolicies)) {
          return new RoomOpenHelper.ValidationResult(false, "policies(com.policyreminder.app.data.model.Policy).\n"
                  + " Expected:\n" + _infoPolicies + "\n"
                  + " Found:\n" + _existingPolicies);
        }
        final HashMap<String, TableInfo.Column> _columnsReminders = new HashMap<String, TableInfo.Column>(12);
        _columnsReminders.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("customerId", new TableInfo.Column("customerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("policyId", new TableInfo.Column("policyId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("familyMemberId", new TableInfo.Column("familyMemberId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("reminderDate", new TableInfo.Column("reminderDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("isSnoozed", new TableInfo.Column("isSnoozed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("snoozeUntil", new TableInfo.Column("snoozeUntil", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReminders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReminders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReminders = new TableInfo("reminders", _columnsReminders, _foreignKeysReminders, _indicesReminders);
        final TableInfo _existingReminders = TableInfo.read(db, "reminders");
        if (!_infoReminders.equals(_existingReminders)) {
          return new RoomOpenHelper.ValidationResult(false, "reminders(com.policyreminder.app.data.model.Reminder).\n"
                  + " Expected:\n" + _infoReminders + "\n"
                  + " Found:\n" + _existingReminders);
        }
        final HashMap<String, TableInfo.Column> _columnsCallLogs = new HashMap<String, TableInfo.Column>(8);
        _columnsCallLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLogs.put("customerId", new TableInfo.Column("customerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLogs.put("policyId", new TableInfo.Column("policyId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLogs.put("callDate", new TableInfo.Column("callDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLogs.put("outcome", new TableInfo.Column("outcome", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLogs.put("followUpDate", new TableInfo.Column("followUpDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLogs.put("remarks", new TableInfo.Column("remarks", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLogs.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCallLogs = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysCallLogs.add(new TableInfo.ForeignKey("customers", "CASCADE", "NO ACTION", Arrays.asList("customerId"), Arrays.asList("id")));
        _foreignKeysCallLogs.add(new TableInfo.ForeignKey("policies", "CASCADE", "NO ACTION", Arrays.asList("policyId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCallLogs = new HashSet<TableInfo.Index>(2);
        _indicesCallLogs.add(new TableInfo.Index("index_call_logs_customerId", false, Arrays.asList("customerId"), Arrays.asList("ASC")));
        _indicesCallLogs.add(new TableInfo.Index("index_call_logs_policyId", false, Arrays.asList("policyId"), Arrays.asList("ASC")));
        final TableInfo _infoCallLogs = new TableInfo("call_logs", _columnsCallLogs, _foreignKeysCallLogs, _indicesCallLogs);
        final TableInfo _existingCallLogs = TableInfo.read(db, "call_logs");
        if (!_infoCallLogs.equals(_existingCallLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "call_logs(com.policyreminder.app.data.model.CallLog).\n"
                  + " Expected:\n" + _infoCallLogs + "\n"
                  + " Found:\n" + _existingCallLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsMessageTemplates = new HashMap<String, TableInfo.Column>(6);
        _columnsMessageTemplates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageTemplates.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageTemplates.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageTemplates.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageTemplates.put("isDefault", new TableInfo.Column("isDefault", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageTemplates.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMessageTemplates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMessageTemplates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMessageTemplates = new TableInfo("message_templates", _columnsMessageTemplates, _foreignKeysMessageTemplates, _indicesMessageTemplates);
        final TableInfo _existingMessageTemplates = TableInfo.read(db, "message_templates");
        if (!_infoMessageTemplates.equals(_existingMessageTemplates)) {
          return new RoomOpenHelper.ValidationResult(false, "message_templates(com.policyreminder.app.data.model.MessageTemplate).\n"
                  + " Expected:\n" + _infoMessageTemplates + "\n"
                  + " Found:\n" + _existingMessageTemplates);
        }
        final HashMap<String, TableInfo.Column> _columnsRenewalHistory = new HashMap<String, TableInfo.Column>(8);
        _columnsRenewalHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRenewalHistory.put("policyId", new TableInfo.Column("policyId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRenewalHistory.put("previousRenewalDate", new TableInfo.Column("previousRenewalDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRenewalHistory.put("newRenewalDate", new TableInfo.Column("newRenewalDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRenewalHistory.put("previousPremium", new TableInfo.Column("previousPremium", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRenewalHistory.put("newPremium", new TableInfo.Column("newPremium", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRenewalHistory.put("remarks", new TableInfo.Column("remarks", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRenewalHistory.put("renewedAt", new TableInfo.Column("renewedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRenewalHistory = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRenewalHistory.add(new TableInfo.ForeignKey("policies", "CASCADE", "NO ACTION", Arrays.asList("policyId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesRenewalHistory = new HashSet<TableInfo.Index>(1);
        _indicesRenewalHistory.add(new TableInfo.Index("index_renewal_history_policyId", false, Arrays.asList("policyId"), Arrays.asList("ASC")));
        final TableInfo _infoRenewalHistory = new TableInfo("renewal_history", _columnsRenewalHistory, _foreignKeysRenewalHistory, _indicesRenewalHistory);
        final TableInfo _existingRenewalHistory = TableInfo.read(db, "renewal_history");
        if (!_infoRenewalHistory.equals(_existingRenewalHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "renewal_history(com.policyreminder.app.data.model.RenewalHistory).\n"
                  + " Expected:\n" + _infoRenewalHistory + "\n"
                  + " Found:\n" + _existingRenewalHistory);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5d593f1903b195234441610e2f32df7d", "1751657e885fbe2c86d0d2f5731f26f4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "customers","family_members","policies","reminders","call_logs","message_templates","renewal_history");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `customers`");
      _db.execSQL("DELETE FROM `family_members`");
      _db.execSQL("DELETE FROM `policies`");
      _db.execSQL("DELETE FROM `reminders`");
      _db.execSQL("DELETE FROM `call_logs`");
      _db.execSQL("DELETE FROM `message_templates`");
      _db.execSQL("DELETE FROM `renewal_history`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CustomerDao.class, CustomerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FamilyMemberDao.class, FamilyMemberDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PolicyDao.class, PolicyDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReminderDao.class, ReminderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CallLogDao.class, CallLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MessageTemplateDao.class, MessageTemplateDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RenewalHistoryDao.class, RenewalHistoryDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CustomerDao customerDao() {
    if (_customerDao != null) {
      return _customerDao;
    } else {
      synchronized(this) {
        if(_customerDao == null) {
          _customerDao = new CustomerDao_Impl(this);
        }
        return _customerDao;
      }
    }
  }

  @Override
  public FamilyMemberDao familyMemberDao() {
    if (_familyMemberDao != null) {
      return _familyMemberDao;
    } else {
      synchronized(this) {
        if(_familyMemberDao == null) {
          _familyMemberDao = new FamilyMemberDao_Impl(this);
        }
        return _familyMemberDao;
      }
    }
  }

  @Override
  public PolicyDao policyDao() {
    if (_policyDao != null) {
      return _policyDao;
    } else {
      synchronized(this) {
        if(_policyDao == null) {
          _policyDao = new PolicyDao_Impl(this);
        }
        return _policyDao;
      }
    }
  }

  @Override
  public ReminderDao reminderDao() {
    if (_reminderDao != null) {
      return _reminderDao;
    } else {
      synchronized(this) {
        if(_reminderDao == null) {
          _reminderDao = new ReminderDao_Impl(this);
        }
        return _reminderDao;
      }
    }
  }

  @Override
  public CallLogDao callLogDao() {
    if (_callLogDao != null) {
      return _callLogDao;
    } else {
      synchronized(this) {
        if(_callLogDao == null) {
          _callLogDao = new CallLogDao_Impl(this);
        }
        return _callLogDao;
      }
    }
  }

  @Override
  public MessageTemplateDao messageTemplateDao() {
    if (_messageTemplateDao != null) {
      return _messageTemplateDao;
    } else {
      synchronized(this) {
        if(_messageTemplateDao == null) {
          _messageTemplateDao = new MessageTemplateDao_Impl(this);
        }
        return _messageTemplateDao;
      }
    }
  }

  @Override
  public RenewalHistoryDao renewalHistoryDao() {
    if (_renewalHistoryDao != null) {
      return _renewalHistoryDao;
    } else {
      synchronized(this) {
        if(_renewalHistoryDao == null) {
          _renewalHistoryDao = new RenewalHistoryDao_Impl(this);
        }
        return _renewalHistoryDao;
      }
    }
  }
}
