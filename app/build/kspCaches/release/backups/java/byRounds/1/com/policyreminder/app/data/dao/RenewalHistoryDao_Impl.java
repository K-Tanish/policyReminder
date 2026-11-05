package com.policyreminder.app.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.policyreminder.app.data.model.RenewalHistory;
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
public final class RenewalHistoryDao_Impl implements RenewalHistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RenewalHistory> __insertionAdapterOfRenewalHistory;

  private final SharedSQLiteStatement __preparedStmtOfDeleteRenewalHistoryByPolicyId;

  public RenewalHistoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRenewalHistory = new EntityInsertionAdapter<RenewalHistory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `renewal_history` (`id`,`policyId`,`previousRenewalDate`,`newRenewalDate`,`previousPremium`,`newPremium`,`remarks`,`renewedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RenewalHistory entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPolicyId());
        statement.bindLong(3, entity.getPreviousRenewalDate());
        statement.bindLong(4, entity.getNewRenewalDate());
        statement.bindDouble(5, entity.getPreviousPremium());
        statement.bindDouble(6, entity.getNewPremium());
        statement.bindString(7, entity.getRemarks());
        statement.bindLong(8, entity.getRenewedAt());
      }
    };
    this.__preparedStmtOfDeleteRenewalHistoryByPolicyId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM renewal_history WHERE policyId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertRenewalHistory(final RenewalHistory history,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRenewalHistory.insertAndReturnId(history);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRenewalHistoryByPolicyId(final long policyId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteRenewalHistoryByPolicyId.acquire();
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
          __preparedStmtOfDeleteRenewalHistoryByPolicyId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RenewalHistory>> getRenewalHistoryByPolicyId(final long policyId) {
    final String _sql = "SELECT * FROM renewal_history WHERE policyId = ? ORDER BY renewedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, policyId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"renewal_history"}, new Callable<List<RenewalHistory>>() {
      @Override
      @NonNull
      public List<RenewalHistory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPolicyId = CursorUtil.getColumnIndexOrThrow(_cursor, "policyId");
          final int _cursorIndexOfPreviousRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "previousRenewalDate");
          final int _cursorIndexOfNewRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "newRenewalDate");
          final int _cursorIndexOfPreviousPremium = CursorUtil.getColumnIndexOrThrow(_cursor, "previousPremium");
          final int _cursorIndexOfNewPremium = CursorUtil.getColumnIndexOrThrow(_cursor, "newPremium");
          final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
          final int _cursorIndexOfRenewedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "renewedAt");
          final List<RenewalHistory> _result = new ArrayList<RenewalHistory>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RenewalHistory _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPolicyId;
            _tmpPolicyId = _cursor.getLong(_cursorIndexOfPolicyId);
            final long _tmpPreviousRenewalDate;
            _tmpPreviousRenewalDate = _cursor.getLong(_cursorIndexOfPreviousRenewalDate);
            final long _tmpNewRenewalDate;
            _tmpNewRenewalDate = _cursor.getLong(_cursorIndexOfNewRenewalDate);
            final double _tmpPreviousPremium;
            _tmpPreviousPremium = _cursor.getDouble(_cursorIndexOfPreviousPremium);
            final double _tmpNewPremium;
            _tmpNewPremium = _cursor.getDouble(_cursorIndexOfNewPremium);
            final String _tmpRemarks;
            _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
            final long _tmpRenewedAt;
            _tmpRenewedAt = _cursor.getLong(_cursorIndexOfRenewedAt);
            _item = new RenewalHistory(_tmpId,_tmpPolicyId,_tmpPreviousRenewalDate,_tmpNewRenewalDate,_tmpPreviousPremium,_tmpNewPremium,_tmpRemarks,_tmpRenewedAt);
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
