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
import com.policyreminder.app.data.model.Policy;
import com.policyreminder.app.data.model.PolicyStatus;
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
public final class PolicyDao_Impl implements PolicyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Policy> __insertionAdapterOfPolicy;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Policy> __deletionAdapterOfPolicy;

  private final EntityDeletionOrUpdateAdapter<Policy> __updateAdapterOfPolicy;

  private final SharedSQLiteStatement __preparedStmtOfDeletePolicyById;

  public PolicyDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPolicy = new EntityInsertionAdapter<Policy>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `policies` (`id`,`customerId`,`policyNumber`,`insuranceCompany`,`policyType`,`policyholderName`,`startDate`,`renewalDate`,`premiumAmount`,`paymentFrequency`,`status`,`notes`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Policy entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        statement.bindString(3, entity.getPolicyNumber());
        statement.bindString(4, entity.getInsuranceCompany());
        statement.bindString(5, entity.getPolicyType());
        statement.bindString(6, entity.getPolicyholderName());
        statement.bindLong(7, entity.getStartDate());
        statement.bindLong(8, entity.getRenewalDate());
        statement.bindDouble(9, entity.getPremiumAmount());
        statement.bindString(10, entity.getPaymentFrequency());
        final String _tmp = __converters.fromPolicyStatus(entity.getStatus());
        statement.bindString(11, _tmp);
        statement.bindString(12, entity.getNotes());
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfPolicy = new EntityDeletionOrUpdateAdapter<Policy>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `policies` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Policy entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPolicy = new EntityDeletionOrUpdateAdapter<Policy>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `policies` SET `id` = ?,`customerId` = ?,`policyNumber` = ?,`insuranceCompany` = ?,`policyType` = ?,`policyholderName` = ?,`startDate` = ?,`renewalDate` = ?,`premiumAmount` = ?,`paymentFrequency` = ?,`status` = ?,`notes` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Policy entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        statement.bindString(3, entity.getPolicyNumber());
        statement.bindString(4, entity.getInsuranceCompany());
        statement.bindString(5, entity.getPolicyType());
        statement.bindString(6, entity.getPolicyholderName());
        statement.bindLong(7, entity.getStartDate());
        statement.bindLong(8, entity.getRenewalDate());
        statement.bindDouble(9, entity.getPremiumAmount());
        statement.bindString(10, entity.getPaymentFrequency());
        final String _tmp = __converters.fromPolicyStatus(entity.getStatus());
        statement.bindString(11, _tmp);
        statement.bindString(12, entity.getNotes());
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfDeletePolicyById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM policies WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPolicy(final Policy policy, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPolicy.insertAndReturnId(policy);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePolicy(final Policy policy, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPolicy.handle(policy);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePolicy(final Policy policy, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPolicy.handle(policy);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePolicyById(final long policyId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePolicyById.acquire();
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
          __preparedStmtOfDeletePolicyById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Policy>> getAllPolicies() {
    final String _sql = "SELECT * FROM policies ORDER BY renewalDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"policies"}, new Callable<List<Policy>>() {
      @Override
      @NonNull
      public List<Policy> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "policyNumber");
          final int _cursorIndexOfInsuranceCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceCompany");
          final int _cursorIndexOfPolicyType = CursorUtil.getColumnIndexOrThrow(_cursor, "policyType");
          final int _cursorIndexOfPolicyholderName = CursorUtil.getColumnIndexOrThrow(_cursor, "policyholderName");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "renewalDate");
          final int _cursorIndexOfPremiumAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "premiumAmount");
          final int _cursorIndexOfPaymentFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentFrequency");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Policy> _result = new ArrayList<Policy>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Policy _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final String _tmpPolicyNumber;
            _tmpPolicyNumber = _cursor.getString(_cursorIndexOfPolicyNumber);
            final String _tmpInsuranceCompany;
            _tmpInsuranceCompany = _cursor.getString(_cursorIndexOfInsuranceCompany);
            final String _tmpPolicyType;
            _tmpPolicyType = _cursor.getString(_cursorIndexOfPolicyType);
            final String _tmpPolicyholderName;
            _tmpPolicyholderName = _cursor.getString(_cursorIndexOfPolicyholderName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpRenewalDate;
            _tmpRenewalDate = _cursor.getLong(_cursorIndexOfRenewalDate);
            final double _tmpPremiumAmount;
            _tmpPremiumAmount = _cursor.getDouble(_cursorIndexOfPremiumAmount);
            final String _tmpPaymentFrequency;
            _tmpPaymentFrequency = _cursor.getString(_cursorIndexOfPaymentFrequency);
            final PolicyStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toPolicyStatus(_tmp);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Policy(_tmpId,_tmpCustomerId,_tmpPolicyNumber,_tmpInsuranceCompany,_tmpPolicyType,_tmpPolicyholderName,_tmpStartDate,_tmpRenewalDate,_tmpPremiumAmount,_tmpPaymentFrequency,_tmpStatus,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<Policy> getPolicyById(final long policyId) {
    final String _sql = "SELECT * FROM policies WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, policyId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"policies"}, new Callable<Policy>() {
      @Override
      @Nullable
      public Policy call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "policyNumber");
          final int _cursorIndexOfInsuranceCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceCompany");
          final int _cursorIndexOfPolicyType = CursorUtil.getColumnIndexOrThrow(_cursor, "policyType");
          final int _cursorIndexOfPolicyholderName = CursorUtil.getColumnIndexOrThrow(_cursor, "policyholderName");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "renewalDate");
          final int _cursorIndexOfPremiumAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "premiumAmount");
          final int _cursorIndexOfPaymentFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentFrequency");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final Policy _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final String _tmpPolicyNumber;
            _tmpPolicyNumber = _cursor.getString(_cursorIndexOfPolicyNumber);
            final String _tmpInsuranceCompany;
            _tmpInsuranceCompany = _cursor.getString(_cursorIndexOfInsuranceCompany);
            final String _tmpPolicyType;
            _tmpPolicyType = _cursor.getString(_cursorIndexOfPolicyType);
            final String _tmpPolicyholderName;
            _tmpPolicyholderName = _cursor.getString(_cursorIndexOfPolicyholderName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpRenewalDate;
            _tmpRenewalDate = _cursor.getLong(_cursorIndexOfRenewalDate);
            final double _tmpPremiumAmount;
            _tmpPremiumAmount = _cursor.getDouble(_cursorIndexOfPremiumAmount);
            final String _tmpPaymentFrequency;
            _tmpPaymentFrequency = _cursor.getString(_cursorIndexOfPaymentFrequency);
            final PolicyStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toPolicyStatus(_tmp);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new Policy(_tmpId,_tmpCustomerId,_tmpPolicyNumber,_tmpInsuranceCompany,_tmpPolicyType,_tmpPolicyholderName,_tmpStartDate,_tmpRenewalDate,_tmpPremiumAmount,_tmpPaymentFrequency,_tmpStatus,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Policy>> getPoliciesByCustomerId(final long customerId) {
    final String _sql = "SELECT * FROM policies WHERE customerId = ? ORDER BY renewalDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"policies"}, new Callable<List<Policy>>() {
      @Override
      @NonNull
      public List<Policy> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "policyNumber");
          final int _cursorIndexOfInsuranceCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceCompany");
          final int _cursorIndexOfPolicyType = CursorUtil.getColumnIndexOrThrow(_cursor, "policyType");
          final int _cursorIndexOfPolicyholderName = CursorUtil.getColumnIndexOrThrow(_cursor, "policyholderName");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "renewalDate");
          final int _cursorIndexOfPremiumAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "premiumAmount");
          final int _cursorIndexOfPaymentFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentFrequency");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Policy> _result = new ArrayList<Policy>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Policy _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final String _tmpPolicyNumber;
            _tmpPolicyNumber = _cursor.getString(_cursorIndexOfPolicyNumber);
            final String _tmpInsuranceCompany;
            _tmpInsuranceCompany = _cursor.getString(_cursorIndexOfInsuranceCompany);
            final String _tmpPolicyType;
            _tmpPolicyType = _cursor.getString(_cursorIndexOfPolicyType);
            final String _tmpPolicyholderName;
            _tmpPolicyholderName = _cursor.getString(_cursorIndexOfPolicyholderName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpRenewalDate;
            _tmpRenewalDate = _cursor.getLong(_cursorIndexOfRenewalDate);
            final double _tmpPremiumAmount;
            _tmpPremiumAmount = _cursor.getDouble(_cursorIndexOfPremiumAmount);
            final String _tmpPaymentFrequency;
            _tmpPaymentFrequency = _cursor.getString(_cursorIndexOfPaymentFrequency);
            final PolicyStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toPolicyStatus(_tmp);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Policy(_tmpId,_tmpCustomerId,_tmpPolicyNumber,_tmpInsuranceCompany,_tmpPolicyType,_tmpPolicyholderName,_tmpStartDate,_tmpRenewalDate,_tmpPremiumAmount,_tmpPaymentFrequency,_tmpStatus,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Policy>> searchPolicies(final String query) {
    final String _sql = "\n"
            + "        SELECT * FROM policies \n"
            + "        WHERE policyNumber LIKE '%' || ? || '%'\n"
            + "        OR insuranceCompany LIKE '%' || ? || '%'\n"
            + "        OR policyType LIKE '%' || ? || '%'\n"
            + "        OR policyholderName LIKE '%' || ? || '%'\n"
            + "        ORDER BY renewalDate ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    _argIndex = 4;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"policies"}, new Callable<List<Policy>>() {
      @Override
      @NonNull
      public List<Policy> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "policyNumber");
          final int _cursorIndexOfInsuranceCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceCompany");
          final int _cursorIndexOfPolicyType = CursorUtil.getColumnIndexOrThrow(_cursor, "policyType");
          final int _cursorIndexOfPolicyholderName = CursorUtil.getColumnIndexOrThrow(_cursor, "policyholderName");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "renewalDate");
          final int _cursorIndexOfPremiumAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "premiumAmount");
          final int _cursorIndexOfPaymentFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentFrequency");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Policy> _result = new ArrayList<Policy>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Policy _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final String _tmpPolicyNumber;
            _tmpPolicyNumber = _cursor.getString(_cursorIndexOfPolicyNumber);
            final String _tmpInsuranceCompany;
            _tmpInsuranceCompany = _cursor.getString(_cursorIndexOfInsuranceCompany);
            final String _tmpPolicyType;
            _tmpPolicyType = _cursor.getString(_cursorIndexOfPolicyType);
            final String _tmpPolicyholderName;
            _tmpPolicyholderName = _cursor.getString(_cursorIndexOfPolicyholderName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpRenewalDate;
            _tmpRenewalDate = _cursor.getLong(_cursorIndexOfRenewalDate);
            final double _tmpPremiumAmount;
            _tmpPremiumAmount = _cursor.getDouble(_cursorIndexOfPremiumAmount);
            final String _tmpPaymentFrequency;
            _tmpPaymentFrequency = _cursor.getString(_cursorIndexOfPaymentFrequency);
            final PolicyStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toPolicyStatus(_tmp);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Policy(_tmpId,_tmpCustomerId,_tmpPolicyNumber,_tmpInsuranceCompany,_tmpPolicyType,_tmpPolicyholderName,_tmpStartDate,_tmpRenewalDate,_tmpPremiumAmount,_tmpPaymentFrequency,_tmpStatus,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Policy>> getPoliciesByStatus(final PolicyStatus status) {
    final String _sql = "SELECT * FROM policies WHERE status = ? ORDER BY renewalDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromPolicyStatus(status);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"policies"}, new Callable<List<Policy>>() {
      @Override
      @NonNull
      public List<Policy> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "policyNumber");
          final int _cursorIndexOfInsuranceCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceCompany");
          final int _cursorIndexOfPolicyType = CursorUtil.getColumnIndexOrThrow(_cursor, "policyType");
          final int _cursorIndexOfPolicyholderName = CursorUtil.getColumnIndexOrThrow(_cursor, "policyholderName");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "renewalDate");
          final int _cursorIndexOfPremiumAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "premiumAmount");
          final int _cursorIndexOfPaymentFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentFrequency");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Policy> _result = new ArrayList<Policy>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Policy _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final String _tmpPolicyNumber;
            _tmpPolicyNumber = _cursor.getString(_cursorIndexOfPolicyNumber);
            final String _tmpInsuranceCompany;
            _tmpInsuranceCompany = _cursor.getString(_cursorIndexOfInsuranceCompany);
            final String _tmpPolicyType;
            _tmpPolicyType = _cursor.getString(_cursorIndexOfPolicyType);
            final String _tmpPolicyholderName;
            _tmpPolicyholderName = _cursor.getString(_cursorIndexOfPolicyholderName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpRenewalDate;
            _tmpRenewalDate = _cursor.getLong(_cursorIndexOfRenewalDate);
            final double _tmpPremiumAmount;
            _tmpPremiumAmount = _cursor.getDouble(_cursorIndexOfPremiumAmount);
            final String _tmpPaymentFrequency;
            _tmpPaymentFrequency = _cursor.getString(_cursorIndexOfPaymentFrequency);
            final PolicyStatus _tmpStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toPolicyStatus(_tmp_1);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Policy(_tmpId,_tmpCustomerId,_tmpPolicyNumber,_tmpInsuranceCompany,_tmpPolicyType,_tmpPolicyholderName,_tmpStartDate,_tmpRenewalDate,_tmpPremiumAmount,_tmpPaymentFrequency,_tmpStatus,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Policy>> getPoliciesByDateRange(final long startDate, final long endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM policies \n"
            + "        WHERE renewalDate BETWEEN ? AND ? \n"
            + "        ORDER BY renewalDate ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"policies"}, new Callable<List<Policy>>() {
      @Override
      @NonNull
      public List<Policy> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPolicyNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "policyNumber");
          final int _cursorIndexOfInsuranceCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceCompany");
          final int _cursorIndexOfPolicyType = CursorUtil.getColumnIndexOrThrow(_cursor, "policyType");
          final int _cursorIndexOfPolicyholderName = CursorUtil.getColumnIndexOrThrow(_cursor, "policyholderName");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "renewalDate");
          final int _cursorIndexOfPremiumAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "premiumAmount");
          final int _cursorIndexOfPaymentFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentFrequency");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Policy> _result = new ArrayList<Policy>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Policy _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final String _tmpPolicyNumber;
            _tmpPolicyNumber = _cursor.getString(_cursorIndexOfPolicyNumber);
            final String _tmpInsuranceCompany;
            _tmpInsuranceCompany = _cursor.getString(_cursorIndexOfInsuranceCompany);
            final String _tmpPolicyType;
            _tmpPolicyType = _cursor.getString(_cursorIndexOfPolicyType);
            final String _tmpPolicyholderName;
            _tmpPolicyholderName = _cursor.getString(_cursorIndexOfPolicyholderName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpRenewalDate;
            _tmpRenewalDate = _cursor.getLong(_cursorIndexOfRenewalDate);
            final double _tmpPremiumAmount;
            _tmpPremiumAmount = _cursor.getDouble(_cursorIndexOfPremiumAmount);
            final String _tmpPaymentFrequency;
            _tmpPaymentFrequency = _cursor.getString(_cursorIndexOfPaymentFrequency);
            final PolicyStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toPolicyStatus(_tmp);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Policy(_tmpId,_tmpCustomerId,_tmpPolicyNumber,_tmpInsuranceCompany,_tmpPolicyType,_tmpPolicyholderName,_tmpStartDate,_tmpRenewalDate,_tmpPremiumAmount,_tmpPaymentFrequency,_tmpStatus,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<Integer> getPolicyCount() {
    final String _sql = "SELECT COUNT(*) FROM policies";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"policies"}, new Callable<Integer>() {
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

  @Override
  public Flow<Integer> getPolicyCountByCustomer(final long customerId) {
    final String _sql = "SELECT COUNT(*) FROM policies WHERE customerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"policies"}, new Callable<Integer>() {
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
