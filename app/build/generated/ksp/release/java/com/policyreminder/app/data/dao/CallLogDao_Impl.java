package com.policyreminder.app.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.policyreminder.app.data.database.Converters;
import com.policyreminder.app.data.model.CallLog;
import com.policyreminder.app.data.model.CallOutcome;
import java.lang.Class;
import java.lang.Exception;
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
public final class CallLogDao_Impl implements CallLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CallLog> __insertionAdapterOfCallLog;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<CallLog> __deletionAdapterOfCallLog;

  private final EntityDeletionOrUpdateAdapter<CallLog> __updateAdapterOfCallLog;

  public CallLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCallLog = new EntityInsertionAdapter<CallLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `call_logs` (`id`,`customerId`,`policyId`,`callDate`,`outcome`,`followUpDate`,`remarks`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CallLog entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        if (entity.getPolicyId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getPolicyId());
        }
        statement.bindLong(4, entity.getCallDate());
        final String _tmp = __converters.fromCallOutcome(entity.getOutcome());
        statement.bindString(5, _tmp);
        if (entity.getFollowUpDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getFollowUpDate());
        }
        statement.bindString(7, entity.getRemarks());
        statement.bindLong(8, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfCallLog = new EntityDeletionOrUpdateAdapter<CallLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `call_logs` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CallLog entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCallLog = new EntityDeletionOrUpdateAdapter<CallLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `call_logs` SET `id` = ?,`customerId` = ?,`policyId` = ?,`callDate` = ?,`outcome` = ?,`followUpDate` = ?,`remarks` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CallLog entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        if (entity.getPolicyId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getPolicyId());
        }
        statement.bindLong(4, entity.getCallDate());
        final String _tmp = __converters.fromCallOutcome(entity.getOutcome());
        statement.bindString(5, _tmp);
        if (entity.getFollowUpDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getFollowUpDate());
        }
        statement.bindString(7, entity.getRemarks());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public Object insertCallLog(final CallLog callLog, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCallLog.insertAndReturnId(callLog);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCallLog(final CallLog callLog, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCallLog.handle(callLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCallLog(final CallLog callLog, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCallLog.handle(callLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CallLog>> getAllCallLogs() {
    final String _sql = "SELECT * FROM call_logs ORDER BY callDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_logs"}, new Callable<List<CallLog>>() {
      @Override
      @NonNull
      public List<CallLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfCallDate = CursorUtil.getColumnIndexOrThrow(_cursor, "callDate");
          final int _cursorIndexOfOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "outcome");
          final int _cursorIndexOfFollowUpDate = CursorUtil.getColumnIndexOrThrow(_cursor, "followUpDate");
          final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CallLog> _result = new ArrayList<CallLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallLog _item;
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
            final long _tmpCallDate;
            _tmpCallDate = _cursor.getLong(_cursorIndexOfCallDate);
            final CallOutcome _tmpOutcome;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfOutcome);
            _tmpOutcome = __converters.toCallOutcome(_tmp);
            final Long _tmpFollowUpDate;
            if (_cursor.isNull(_cursorIndexOfFollowUpDate)) {
              _tmpFollowUpDate = null;
            } else {
              _tmpFollowUpDate = _cursor.getLong(_cursorIndexOfFollowUpDate);
            }
            final String _tmpRemarks;
            _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CallLog(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpCallDate,_tmpOutcome,_tmpFollowUpDate,_tmpRemarks,_tmpCreatedAt);
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
  public Flow<CallLog> getCallLogById(final long logId) {
    final String _sql = "SELECT * FROM call_logs WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, logId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_logs"}, new Callable<CallLog>() {
      @Override
      @Nullable
      public CallLog call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfCallDate = CursorUtil.getColumnIndexOrThrow(_cursor, "callDate");
          final int _cursorIndexOfOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "outcome");
          final int _cursorIndexOfFollowUpDate = CursorUtil.getColumnIndexOrThrow(_cursor, "followUpDate");
          final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final CallLog _result;
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
            final long _tmpCallDate;
            _tmpCallDate = _cursor.getLong(_cursorIndexOfCallDate);
            final CallOutcome _tmpOutcome;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfOutcome);
            _tmpOutcome = __converters.toCallOutcome(_tmp);
            final Long _tmpFollowUpDate;
            if (_cursor.isNull(_cursorIndexOfFollowUpDate)) {
              _tmpFollowUpDate = null;
            } else {
              _tmpFollowUpDate = _cursor.getLong(_cursorIndexOfFollowUpDate);
            }
            final String _tmpRemarks;
            _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new CallLog(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpCallDate,_tmpOutcome,_tmpFollowUpDate,_tmpRemarks,_tmpCreatedAt);
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
  public Flow<List<CallLog>> getCallLogsByCustomerId(final long customerId) {
    final String _sql = "SELECT * FROM call_logs WHERE customerId = ? ORDER BY callDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_logs"}, new Callable<List<CallLog>>() {
      @Override
      @NonNull
      public List<CallLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfCallDate = CursorUtil.getColumnIndexOrThrow(_cursor, "callDate");
          final int _cursorIndexOfOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "outcome");
          final int _cursorIndexOfFollowUpDate = CursorUtil.getColumnIndexOrThrow(_cursor, "followUpDate");
          final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CallLog> _result = new ArrayList<CallLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallLog _item;
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
            final long _tmpCallDate;
            _tmpCallDate = _cursor.getLong(_cursorIndexOfCallDate);
            final CallOutcome _tmpOutcome;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfOutcome);
            _tmpOutcome = __converters.toCallOutcome(_tmp);
            final Long _tmpFollowUpDate;
            if (_cursor.isNull(_cursorIndexOfFollowUpDate)) {
              _tmpFollowUpDate = null;
            } else {
              _tmpFollowUpDate = _cursor.getLong(_cursorIndexOfFollowUpDate);
            }
            final String _tmpRemarks;
            _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CallLog(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpCallDate,_tmpOutcome,_tmpFollowUpDate,_tmpRemarks,_tmpCreatedAt);
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
  public Flow<List<CallLog>> getCallLogsByPolicyId(final long policyId) {
    final String _sql = "SELECT * FROM call_logs WHERE policyId = ? ORDER BY callDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, policyId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_logs"}, new Callable<List<CallLog>>() {
      @Override
      @NonNull
      public List<CallLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfCallDate = CursorUtil.getColumnIndexOrThrow(_cursor, "callDate");
          final int _cursorIndexOfOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "outcome");
          final int _cursorIndexOfFollowUpDate = CursorUtil.getColumnIndexOrThrow(_cursor, "followUpDate");
          final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CallLog> _result = new ArrayList<CallLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallLog _item;
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
            final long _tmpCallDate;
            _tmpCallDate = _cursor.getLong(_cursorIndexOfCallDate);
            final CallOutcome _tmpOutcome;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfOutcome);
            _tmpOutcome = __converters.toCallOutcome(_tmp);
            final Long _tmpFollowUpDate;
            if (_cursor.isNull(_cursorIndexOfFollowUpDate)) {
              _tmpFollowUpDate = null;
            } else {
              _tmpFollowUpDate = _cursor.getLong(_cursorIndexOfFollowUpDate);
            }
            final String _tmpRemarks;
            _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CallLog(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpCallDate,_tmpOutcome,_tmpFollowUpDate,_tmpRemarks,_tmpCreatedAt);
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
  public Flow<List<CallLog>> getCallLogsByOutcome(final CallOutcome outcome) {
    final String _sql = "SELECT * FROM call_logs WHERE outcome = ? ORDER BY callDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromCallOutcome(outcome);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_logs"}, new Callable<List<CallLog>>() {
      @Override
      @NonNull
      public List<CallLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfCallDate = CursorUtil.getColumnIndexOrThrow(_cursor, "callDate");
          final int _cursorIndexOfOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "outcome");
          final int _cursorIndexOfFollowUpDate = CursorUtil.getColumnIndexOrThrow(_cursor, "followUpDate");
          final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CallLog> _result = new ArrayList<CallLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallLog _item;
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
            final long _tmpCallDate;
            _tmpCallDate = _cursor.getLong(_cursorIndexOfCallDate);
            final CallOutcome _tmpOutcome;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfOutcome);
            _tmpOutcome = __converters.toCallOutcome(_tmp_1);
            final Long _tmpFollowUpDate;
            if (_cursor.isNull(_cursorIndexOfFollowUpDate)) {
              _tmpFollowUpDate = null;
            } else {
              _tmpFollowUpDate = _cursor.getLong(_cursorIndexOfFollowUpDate);
            }
            final String _tmpRemarks;
            _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CallLog(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpCallDate,_tmpOutcome,_tmpFollowUpDate,_tmpRemarks,_tmpCreatedAt);
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
  public Flow<List<CallLog>> getCallLogsWithUpcomingFollowUp(final long date) {
    final String _sql = "\n"
            + "        SELECT * FROM call_logs \n"
            + "        WHERE followUpDate IS NOT NULL \n"
            + "        AND followUpDate <= ? \n"
            + "        ORDER BY followUpDate ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_logs"}, new Callable<List<CallLog>>() {
      @Override
      @NonNull
      public List<CallLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfCallDate = CursorUtil.getColumnIndexOrThrow(_cursor, "callDate");
          final int _cursorIndexOfOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "outcome");
          final int _cursorIndexOfFollowUpDate = CursorUtil.getColumnIndexOrThrow(_cursor, "followUpDate");
          final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CallLog> _result = new ArrayList<CallLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallLog _item;
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
            final long _tmpCallDate;
            _tmpCallDate = _cursor.getLong(_cursorIndexOfCallDate);
            final CallOutcome _tmpOutcome;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfOutcome);
            _tmpOutcome = __converters.toCallOutcome(_tmp);
            final Long _tmpFollowUpDate;
            if (_cursor.isNull(_cursorIndexOfFollowUpDate)) {
              _tmpFollowUpDate = null;
            } else {
              _tmpFollowUpDate = _cursor.getLong(_cursorIndexOfFollowUpDate);
            }
            final String _tmpRemarks;
            _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CallLog(_tmpId,_tmpCustomerId,_tmpPolicyId,_tmpCallDate,_tmpOutcome,_tmpFollowUpDate,_tmpRemarks,_tmpCreatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
