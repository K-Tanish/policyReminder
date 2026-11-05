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
import com.policyreminder.app.data.model.Customer;
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
public final class CustomerDao_Impl implements CustomerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Customer> __insertionAdapterOfCustomer;

  private final EntityDeletionOrUpdateAdapter<Customer> __deletionAdapterOfCustomer;

  private final EntityDeletionOrUpdateAdapter<Customer> __updateAdapterOfCustomer;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCustomerById;

  public CustomerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCustomer = new EntityInsertionAdapter<Customer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `customers` (`id`,`firstName`,`middleName`,`surname`,`mobileNumber`,`alternateMobile`,`email`,`address`,`dateOfBirth`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Customer entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getFirstName());
        statement.bindString(3, entity.getMiddleName());
        statement.bindString(4, entity.getSurname());
        statement.bindString(5, entity.getMobileNumber());
        statement.bindString(6, entity.getAlternateMobile());
        statement.bindString(7, entity.getEmail());
        statement.bindString(8, entity.getAddress());
        if (entity.getDateOfBirth() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getDateOfBirth());
        }
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfCustomer = new EntityDeletionOrUpdateAdapter<Customer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `customers` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Customer entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCustomer = new EntityDeletionOrUpdateAdapter<Customer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `customers` SET `id` = ?,`firstName` = ?,`middleName` = ?,`surname` = ?,`mobileNumber` = ?,`alternateMobile` = ?,`email` = ?,`address` = ?,`dateOfBirth` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Customer entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getFirstName());
        statement.bindString(3, entity.getMiddleName());
        statement.bindString(4, entity.getSurname());
        statement.bindString(5, entity.getMobileNumber());
        statement.bindString(6, entity.getAlternateMobile());
        statement.bindString(7, entity.getEmail());
        statement.bindString(8, entity.getAddress());
        if (entity.getDateOfBirth() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getDateOfBirth());
        }
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteCustomerById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM customers WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertCustomer(final Customer customer,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCustomer.insertAndReturnId(customer);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCustomer(final Customer customer,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCustomer.handle(customer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCustomer(final Customer customer,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCustomer.handle(customer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCustomerById(final long customerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCustomerById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, customerId);
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
          __preparedStmtOfDeleteCustomerById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Customer>> getAllCustomers() {
    final String _sql = "SELECT * FROM customers ORDER BY firstName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"customers"}, new Callable<List<Customer>>() {
      @Override
      @NonNull
      public List<Customer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfSurname = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
          final int _cursorIndexOfMobileNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "mobileNumber");
          final int _cursorIndexOfAlternateMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "alternateMobile");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Customer> _result = new ArrayList<Customer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Customer _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpFirstName;
            _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            final String _tmpMiddleName;
            _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            final String _tmpSurname;
            _tmpSurname = _cursor.getString(_cursorIndexOfSurname);
            final String _tmpMobileNumber;
            _tmpMobileNumber = _cursor.getString(_cursorIndexOfMobileNumber);
            final String _tmpAlternateMobile;
            _tmpAlternateMobile = _cursor.getString(_cursorIndexOfAlternateMobile);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final Long _tmpDateOfBirth;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmpDateOfBirth = null;
            } else {
              _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Customer(_tmpId,_tmpFirstName,_tmpMiddleName,_tmpSurname,_tmpMobileNumber,_tmpAlternateMobile,_tmpEmail,_tmpAddress,_tmpDateOfBirth,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<Customer> getCustomerById(final long customerId) {
    final String _sql = "SELECT * FROM customers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"customers"}, new Callable<Customer>() {
      @Override
      @Nullable
      public Customer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfSurname = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
          final int _cursorIndexOfMobileNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "mobileNumber");
          final int _cursorIndexOfAlternateMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "alternateMobile");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final Customer _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpFirstName;
            _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            final String _tmpMiddleName;
            _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            final String _tmpSurname;
            _tmpSurname = _cursor.getString(_cursorIndexOfSurname);
            final String _tmpMobileNumber;
            _tmpMobileNumber = _cursor.getString(_cursorIndexOfMobileNumber);
            final String _tmpAlternateMobile;
            _tmpAlternateMobile = _cursor.getString(_cursorIndexOfAlternateMobile);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final Long _tmpDateOfBirth;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmpDateOfBirth = null;
            } else {
              _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new Customer(_tmpId,_tmpFirstName,_tmpMiddleName,_tmpSurname,_tmpMobileNumber,_tmpAlternateMobile,_tmpEmail,_tmpAddress,_tmpDateOfBirth,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Customer>> searchCustomers(final String query) {
    final String _sql = "\n"
            + "        SELECT * FROM customers \n"
            + "        WHERE firstName LIKE '%' || ? || '%' \n"
            + "        OR surname LIKE '%' || ? || '%'\n"
            + "        OR mobileNumber LIKE '%' || ? || '%'\n"
            + "        ORDER BY firstName ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"customers"}, new Callable<List<Customer>>() {
      @Override
      @NonNull
      public List<Customer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
          final int _cursorIndexOfMiddleName = CursorUtil.getColumnIndexOrThrow(_cursor, "middleName");
          final int _cursorIndexOfSurname = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
          final int _cursorIndexOfMobileNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "mobileNumber");
          final int _cursorIndexOfAlternateMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "alternateMobile");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Customer> _result = new ArrayList<Customer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Customer _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpFirstName;
            _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            final String _tmpMiddleName;
            _tmpMiddleName = _cursor.getString(_cursorIndexOfMiddleName);
            final String _tmpSurname;
            _tmpSurname = _cursor.getString(_cursorIndexOfSurname);
            final String _tmpMobileNumber;
            _tmpMobileNumber = _cursor.getString(_cursorIndexOfMobileNumber);
            final String _tmpAlternateMobile;
            _tmpAlternateMobile = _cursor.getString(_cursorIndexOfAlternateMobile);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final Long _tmpDateOfBirth;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmpDateOfBirth = null;
            } else {
              _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Customer(_tmpId,_tmpFirstName,_tmpMiddleName,_tmpSurname,_tmpMobileNumber,_tmpAlternateMobile,_tmpEmail,_tmpAddress,_tmpDateOfBirth,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<Integer> getCustomerCount() {
    final String _sql = "SELECT COUNT(*) FROM customers";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"customers"}, new Callable<Integer>() {
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
