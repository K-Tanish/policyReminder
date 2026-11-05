package com.policyreminder.app.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.policyreminder.app.data.database.Converters;
import com.policyreminder.app.data.model.Reminder;
import com.policyreminder.app.data.model.ReminderType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ReminderDao_Impl implements ReminderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Reminder> __insertionAdapterOfReminder;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Reminder> __deletionAdapterOfReminder;

  private final EntityDeletionOrUpdateAdapter<Reminder> __updateAdapterOfReminder;

  private final SharedSQLiteStatement __preparedStmtOfDeleteReminderById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteRenewalRemindersByPolicyId;

  public ReminderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReminder = new EntityInsertionAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reminders` (`id`,`customerId`,`policyId`,`familyMemberId`,`type`,`reminderDate`,`title`,`description`,`isCompleted`,`isSnoozed`,`snoozeUntil`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Reminder entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        if (entity.getPolicyId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getPolicyId());
        }
        if (entity.getFamilyMemberId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getFamilyMemberId());
        }
        final String _tmp = __converters.fromReminderType(entity.getType());
        statement.bindString(5, _tmp);
        statement.bindLong(6, entity.getReminderDate());
        statement.bindString(7, entity.getTitle());
        statement.bindString(8, entity.getDescription());
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        final int _tmp_2 = entity.isSnoozed() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        if (entity.getSnoozeUntil() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getSnoozeUntil());
        }
        statement.bindLong(12, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfReminder = new EntityDeletionOrUpdateAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `reminders` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Reminder entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfReminder = new EntityDeletionOrUpdateAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reminders` SET `id` = ?,`customerId` = ?,`policyId` = ?,`familyMemberId` = ?,`type` = ?,`reminderDate` = ?,`title` = ?,`description` = ?,`isCompleted` = ?,`isSnoozed` = ?,`snoozeUntil` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Reminder entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        if (entity.getPolicyId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getPolicyId());
        }
        if (entity.getFamilyMemberId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getFamilyMemberId());
        }
        final String _tmp = __converters.fromReminderType(entity.getType());
        statement.bindString(5, _tmp);
        statement.bindLong(6, entity.getReminderDate());
        statement.bindString(7, entity.getTitle());
        statement.bindString(8, entity.getDescription());
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        final int _tmp_2 = entity.isSnoozed() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        if (entity.getSnoozeUntil() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getSnoozeUntil());
        }
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteReminderById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM reminders WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteRenewalRemindersByPolicyId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM reminders WHERE policyId = ? AND type = 'POLICY_RENEWAL'";
        return _query;
      }
    };
  }

  @Override
  public Object insertReminder(final Reminder reminder,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfReminder.insertAndReturnId(reminder);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteReminder(final Reminder reminder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfReminder.handle(reminder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateReminder(final Reminder reminder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfReminder.handle(reminder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteReminderById(final long reminderId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteReminderById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, reminderId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteReminderById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRenewalRemindersByPolicyId(final long policyId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteRenewalRemindersByPolicyId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, policyId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteRenewalRemindersByPolicyId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Reminder>> getAllActiveReminders() {
    final String _sql = "SELECT * FROM reminders WHERE isCompleted = 0 ORDER BY reminderDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reminders"}, new Callable<List<Reminder>>() {
      @Override
      @NonNull
      public List<Reminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfFamilyMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "familyMemberId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfReminderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDate");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsSnoozed = CursorUtil.getColumnIndexOrThrow(_cursor, "isSnoozed");
          final int _cursorIndexOfSnoozeUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "snoozeUntil");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reminder _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final Long _tmpPolicyId;
            if (_cursor.isNull(_cursorIndexOfPolicyId)) {
              _tmpPolicyId = null;
            } else {
              _tmpPolicyId = _cursor.getLong(_cursorIndexOfPolicyId);
            }
            final Long _tmpFamilyMemberId;
            if (_cursor.isNull(_cursorIndexOfFamilyMemberId)) {
              _tmpFamilyMemberId = null;
            } else {
              _tmpFamilyMemberId = _cursor.getLong(_cursorIndexOfFamilyMemberId);
            }
            final ReminderType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toReminderType(_tmp);
            final long _tmpReminderDate;
            _tmpReminderDate = _cursor.getLong(_cursorIndexOfReminderDate);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsSnoozed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSnoozed);
            _tmpIsSnoozed = _tmp_2 != 0;
            final Long _tmpSnoozeUntil;
            if (_cursor.isNull(_cursorIndexOfSnoozeUntil)) {
              _tmpSnoozeUntil = null;
            } else {
              _tmpSnoozeUntil = _cursor.getLong(_cursorIndexOfSnoozeUntil);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Reminder(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpFamilyMemberId,_tmpType,_tmpReminderDate,_tmpTitle,_tmpDescription,_tmpIsCompleted,_tmpIsSnoozed,_tmpSnoozeUntil,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Reminder> getReminderById(final long reminderId) {
    final String _sql = "SELECT * FROM reminders WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, reminderId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reminders"}, new Callable<Reminder>() {
      @Override
      @Nullable
      public Reminder call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfFamilyMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "familyMemberId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfReminderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDate");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsSnoozed = CursorUtil.getColumnIndexOrThrow(_cursor, "isSnoozed");
          final int _cursorIndexOfSnoozeUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "snoozeUntil");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Reminder _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final Long _tmpPolicyId;
            if (_cursor.isNull(_cursorIndexOfPolicyId)) {
              _tmpPolicyId = null;
            } else {
              _tmpPolicyId = _cursor.getLong(_cursorIndexOfPolicyId);
            }
            final Long _tmpFamilyMemberId;
            if (_cursor.isNull(_cursorIndexOfFamilyMemberId)) {
              _tmpFamilyMemberId = null;
            } else {
              _tmpFamilyMemberId = _cursor.getLong(_cursorIndexOfFamilyMemberId);
            }
            final ReminderType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toReminderType(_tmp);
            final long _tmpReminderDate;
            _tmpReminderDate = _cursor.getLong(_cursorIndexOfReminderDate);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsSnoozed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSnoozed);
            _tmpIsSnoozed = _tmp_2 != 0;
            final Long _tmpSnoozeUntil;
            if (_cursor.isNull(_cursorIndexOfSnoozeUntil)) {
              _tmpSnoozeUntil = null;
            } else {
              _tmpSnoozeUntil = _cursor.getLong(_cursorIndexOfSnoozeUntil);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Reminder(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpFamilyMemberId,_tmpType,_tmpReminderDate,_tmpTitle,_tmpDescription,_tmpIsCompleted,_tmpIsSnoozed,_tmpSnoozeUntil,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Reminder>> getRemindersByCustomerId(final long customerId) {
    final String _sql = "SELECT * FROM reminders WHERE customerId = ? ORDER BY reminderDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reminders"}, new Callable<List<Reminder>>() {
      @Override
      @NonNull
      public List<Reminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfFamilyMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "familyMemberId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfReminderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDate");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsSnoozed = CursorUtil.getColumnIndexOrThrow(_cursor, "isSnoozed");
          final int _cursorIndexOfSnoozeUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "snoozeUntil");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reminder _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final Long _tmpPolicyId;
            if (_cursor.isNull(_cursorIndexOfPolicyId)) {
              _tmpPolicyId = null;
            } else {
              _tmpPolicyId = _cursor.getLong(_cursorIndexOfPolicyId);
            }
            final Long _tmpFamilyMemberId;
            if (_cursor.isNull(_cursorIndexOfFamilyMemberId)) {
              _tmpFamilyMemberId = null;
            } else {
              _tmpFamilyMemberId = _cursor.getLong(_cursorIndexOfFamilyMemberId);
            }
            final ReminderType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toReminderType(_tmp);
            final long _tmpReminderDate;
            _tmpReminderDate = _cursor.getLong(_cursorIndexOfReminderDate);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsSnoozed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSnoozed);
            _tmpIsSnoozed = _tmp_2 != 0;
            final Long _tmpSnoozeUntil;
            if (_cursor.isNull(_cursorIndexOfSnoozeUntil)) {
              _tmpSnoozeUntil = null;
            } else {
              _tmpSnoozeUntil = _cursor.getLong(_cursorIndexOfSnoozeUntil);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Reminder(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpFamilyMemberId,_tmpType,_tmpReminderDate,_tmpTitle,_tmpDescription,_tmpIsCompleted,_tmpIsSnoozed,_tmpSnoozeUntil,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Reminder>> getRemindersByPolicyId(final long policyId) {
    final String _sql = "SELECT * FROM reminders WHERE policyId = ? ORDER BY reminderDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, policyId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reminders"}, new Callable<List<Reminder>>() {
      @Override
      @NonNull
      public List<Reminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfFamilyMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "familyMemberId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfReminderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDate");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsSnoozed = CursorUtil.getColumnIndexOrThrow(_cursor, "isSnoozed");
          final int _cursorIndexOfSnoozeUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "snoozeUntil");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reminder _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final Long _tmpPolicyId;
            if (_cursor.isNull(_cursorIndexOfPolicyId)) {
              _tmpPolicyId = null;
            } else {
              _tmpPolicyId = _cursor.getLong(_cursorIndexOfPolicyId);
            }
            final Long _tmpFamilyMemberId;
            if (_cursor.isNull(_cursorIndexOfFamilyMemberId)) {
              _tmpFamilyMemberId = null;
            } else {
              _tmpFamilyMemberId = _cursor.getLong(_cursorIndexOfFamilyMemberId);
            }
            final ReminderType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toReminderType(_tmp);
            final long _tmpReminderDate;
            _tmpReminderDate = _cursor.getLong(_cursorIndexOfReminderDate);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsSnoozed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSnoozed);
            _tmpIsSnoozed = _tmp_2 != 0;
            final Long _tmpSnoozeUntil;
            if (_cursor.isNull(_cursorIndexOfSnoozeUntil)) {
              _tmpSnoozeUntil = null;
            } else {
              _tmpSnoozeUntil = _cursor.getLong(_cursorIndexOfSnoozeUntil);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Reminder(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpFamilyMemberId,_tmpType,_tmpReminderDate,_tmpTitle,_tmpDescription,_tmpIsCompleted,_tmpIsSnoozed,_tmpSnoozeUntil,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Reminder>> getRemindersByType(final ReminderType type) {
    final String _sql = "SELECT * FROM reminders WHERE type = ? AND isCompleted = 0 ORDER BY reminderDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromReminderType(type);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reminders"}, new Callable<List<Reminder>>() {
      @Override
      @NonNull
      public List<Reminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfFamilyMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "familyMemberId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfReminderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDate");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsSnoozed = CursorUtil.getColumnIndexOrThrow(_cursor, "isSnoozed");
          final int _cursorIndexOfSnoozeUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "snoozeUntil");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reminder _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final Long _tmpPolicyId;
            if (_cursor.isNull(_cursorIndexOfPolicyId)) {
              _tmpPolicyId = null;
            } else {
              _tmpPolicyId = _cursor.getLong(_cursorIndexOfPolicyId);
            }
            final Long _tmpFamilyMemberId;
            if (_cursor.isNull(_cursorIndexOfFamilyMemberId)) {
              _tmpFamilyMemberId = null;
            } else {
              _tmpFamilyMemberId = _cursor.getLong(_cursorIndexOfFamilyMemberId);
            }
            final ReminderType _tmpType;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toReminderType(_tmp_1);
            final long _tmpReminderDate;
            _tmpReminderDate = _cursor.getLong(_cursorIndexOfReminderDate);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsCompleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_2 != 0;
            final boolean _tmpIsSnoozed;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsSnoozed);
            _tmpIsSnoozed = _tmp_3 != 0;
            final Long _tmpSnoozeUntil;
            if (_cursor.isNull(_cursorIndexOfSnoozeUntil)) {
              _tmpSnoozeUntil = null;
            } else {
              _tmpSnoozeUntil = _cursor.getLong(_cursorIndexOfSnoozeUntil);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Reminder(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpFamilyMemberId,_tmpType,_tmpReminderDate,_tmpTitle,_tmpDescription,_tmpIsCompleted,_tmpIsSnoozed,_tmpSnoozeUntil,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Reminder>> getRemindersInDateRange(final long startDate, final long endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM reminders \n"
            + "        WHERE reminderDate BETWEEN ? AND ? \n"
            + "        AND isCompleted = 0\n"
            + "        ORDER BY reminderDate ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reminders"}, new Callable<List<Reminder>>() {
      @Override
      @NonNull
      public List<Reminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfFamilyMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "familyMemberId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfReminderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDate");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsSnoozed = CursorUtil.getColumnIndexOrThrow(_cursor, "isSnoozed");
          final int _cursorIndexOfSnoozeUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "snoozeUntil");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reminder _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final Long _tmpPolicyId;
            if (_cursor.isNull(_cursorIndexOfPolicyId)) {
              _tmpPolicyId = null;
            } else {
              _tmpPolicyId = _cursor.getLong(_cursorIndexOfPolicyId);
            }
            final Long _tmpFamilyMemberId;
            if (_cursor.isNull(_cursorIndexOfFamilyMemberId)) {
              _tmpFamilyMemberId = null;
            } else {
              _tmpFamilyMemberId = _cursor.getLong(_cursorIndexOfFamilyMemberId);
            }
            final ReminderType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toReminderType(_tmp);
            final long _tmpReminderDate;
            _tmpReminderDate = _cursor.getLong(_cursorIndexOfReminderDate);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsSnoozed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSnoozed);
            _tmpIsSnoozed = _tmp_2 != 0;
            final Long _tmpSnoozeUntil;
            if (_cursor.isNull(_cursorIndexOfSnoozeUntil)) {
              _tmpSnoozeUntil = null;
            } else {
              _tmpSnoozeUntil = _cursor.getLong(_cursorIndexOfSnoozeUntil);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Reminder(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpFamilyMemberId,_tmpType,_tmpReminderDate,_tmpTitle,_tmpDescription,_tmpIsCompleted,_tmpIsSnoozed,_tmpSnoozeUntil,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Reminder>> getUpcomingReminders(final long date) {
    final String _sql = "\n"
            + "        SELECT * FROM reminders \n"
            + "        WHERE reminderDate <= ? \n"
            + "        AND isCompleted = 0\n"
            + "        AND (isSnoozed = 0 OR snoozeUntil <= ?)\n"
            + "        ORDER BY reminderDate ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    _argIndex = 2;
    _statement.bindLong(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reminders"}, new Callable<List<Reminder>>() {
      @Override
      @NonNull
      public List<Reminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfFamilyMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "familyMemberId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfReminderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDate");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsSnoozed = CursorUtil.getColumnIndexOrThrow(_cursor, "isSnoozed");
          final int _cursorIndexOfSnoozeUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "snoozeUntil");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reminder _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final Long _tmpPolicyId;
            if (_cursor.isNull(_cursorIndexOfPolicyId)) {
              _tmpPolicyId = null;
            } else {
              _tmpPolicyId = _cursor.getLong(_cursorIndexOfPolicyId);
            }
            final Long _tmpFamilyMemberId;
            if (_cursor.isNull(_cursorIndexOfFamilyMemberId)) {
              _tmpFamilyMemberId = null;
            } else {
              _tmpFamilyMemberId = _cursor.getLong(_cursorIndexOfFamilyMemberId);
            }
            final ReminderType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toReminderType(_tmp);
            final long _tmpReminderDate;
            _tmpReminderDate = _cursor.getLong(_cursorIndexOfReminderDate);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsSnoozed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSnoozed);
            _tmpIsSnoozed = _tmp_2 != 0;
            final Long _tmpSnoozeUntil;
            if (_cursor.isNull(_cursorIndexOfSnoozeUntil)) {
              _tmpSnoozeUntil = null;
            } else {
              _tmpSnoozeUntil = _cursor.getLong(_cursorIndexOfSnoozeUntil);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Reminder(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpFamilyMemberId,_tmpType,_tmpReminderDate,_tmpTitle,_tmpDescription,_tmpIsCompleted,_tmpIsSnoozed,_tmpSnoozeUntil,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getActiveReminderCount() {
    final String _sql = "SELECT COUNT(*) FROM reminders WHERE isCompleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reminders"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
