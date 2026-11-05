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
import com.policyreminder.app.data.model.FamilyMember;
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
public final class FamilyMemberDao_Impl implements FamilyMemberDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FamilyMember> __insertionAdapterOfFamilyMember;

  private final EntityDeletionOrUpdateAdapter<FamilyMember> __deletionAdapterOfFamilyMember;

  private final EntityDeletionOrUpdateAdapter<FamilyMember> __updateAdapterOfFamilyMember;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFamilyMemberById;

  public FamilyMemberDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFamilyMember = new EntityInsertionAdapter<FamilyMember>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `family_members` (`id`,`customerId`,`name`,`relationship`,`dateOfBirth`,`anniversaryDate`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FamilyMember entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getRelationship());
        if (entity.getDateOfBirth() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getDateOfBirth());
        }
        if (entity.getAnniversaryDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getAnniversaryDate());
        }
        statement.bindLong(7, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfFamilyMember = new EntityDeletionOrUpdateAdapter<FamilyMember>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `family_members` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FamilyMember entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfFamilyMember = new EntityDeletionOrUpdateAdapter<FamilyMember>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `family_members` SET `id` = ?,`customerId` = ?,`name` = ?,`relationship` = ?,`dateOfBirth` = ?,`anniversaryDate` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FamilyMember entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getRelationship());
        if (entity.getDateOfBirth() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getDateOfBirth());
        }
        if (entity.getAnniversaryDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getAnniversaryDate());
        }
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteFamilyMemberById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM family_members WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertFamilyMember(final FamilyMember member,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFamilyMember.insertAndReturnId(member);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFamilyMember(final FamilyMember member,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFamilyMember.handle(member);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateFamilyMember(final FamilyMember member,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfFamilyMember.handle(member);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFamilyMemberById(final long memberId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFamilyMemberById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, memberId);
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
          __preparedStmtOfDeleteFamilyMemberById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FamilyMember>> getFamilyMembersByCustomerId(final long customerId) {
    final String _sql = "SELECT * FROM family_members WHERE customerId = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"family_members"}, new Callable<List<FamilyMember>>() {
      @Override
      @NonNull
      public List<FamilyMember> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRelationship = CursorUtil.getColumnIndexOrThrow(_cursor, "relationship");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfAnniversaryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "anniversaryDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<FamilyMember> _result = new ArrayList<FamilyMember>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FamilyMember _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRelationship;
            _tmpRelationship = _cursor.getString(_cursorIndexOfRelationship);
            final Long _tmpDateOfBirth;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmpDateOfBirth = null;
            } else {
              _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            final Long _tmpAnniversaryDate;
            if (_cursor.isNull(_cursorIndexOfAnniversaryDate)) {
              _tmpAnniversaryDate = null;
            } else {
              _tmpAnniversaryDate = _cursor.getLong(_cursorIndexOfAnniversaryDate);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new FamilyMember(_tmpId,_tmpCustomerId,_tmpName,_tmpRelationship,_tmpDateOfBirth,_tmpAnniversaryDate,_tmpCreatedAt);
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
  public Flow<FamilyMember> getFamilyMemberById(final long memberId) {
    final String _sql = "SELECT * FROM family_members WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, memberId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"family_members"}, new Callable<FamilyMember>() {
      @Override
      @Nullable
      public FamilyMember call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRelationship = CursorUtil.getColumnIndexOrThrow(_cursor, "relationship");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfAnniversaryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "anniversaryDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final FamilyMember _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRelationship;
            _tmpRelationship = _cursor.getString(_cursorIndexOfRelationship);
            final Long _tmpDateOfBirth;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmpDateOfBirth = null;
            } else {
              _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            final Long _tmpAnniversaryDate;
            if (_cursor.isNull(_cursorIndexOfAnniversaryDate)) {
              _tmpAnniversaryDate = null;
            } else {
              _tmpAnniversaryDate = _cursor.getLong(_cursorIndexOfAnniversaryDate);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new FamilyMember(_tmpId,_tmpCustomerId,_tmpName,_tmpRelationship,_tmpDateOfBirth,_tmpAnniversaryDate,_tmpCreatedAt);
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
  public Flow<List<FamilyMember>> getAllFamilyMembersWithBirthdays() {
    final String _sql = "SELECT * FROM family_members WHERE dateOfBirth IS NOT NULL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"family_members"}, new Callable<List<FamilyMember>>() {
      @Override
      @NonNull
      public List<FamilyMember> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRelationship = CursorUtil.getColumnIndexOrThrow(_cursor, "relationship");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfAnniversaryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "anniversaryDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<FamilyMember> _result = new ArrayList<FamilyMember>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FamilyMember _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRelationship;
            _tmpRelationship = _cursor.getString(_cursorIndexOfRelationship);
            final Long _tmpDateOfBirth;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmpDateOfBirth = null;
            } else {
              _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            final Long _tmpAnniversaryDate;
            if (_cursor.isNull(_cursorIndexOfAnniversaryDate)) {
              _tmpAnniversaryDate = null;
            } else {
              _tmpAnniversaryDate = _cursor.getLong(_cursorIndexOfAnniversaryDate);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new FamilyMember(_tmpId,_tmpCustomerId,_tmpName,_tmpRelationship,_tmpDateOfBirth,_tmpAnniversaryDate,_tmpCreatedAt);
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
  public Flow<List<FamilyMember>> getAllFamilyMembersWithAnniversaries() {
    final String _sql = "SELECT * FROM family_members WHERE anniversaryDate IS NOT NULL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"family_members"}, new Callable<List<FamilyMember>>() {
      @Override
      @NonNull
      public List<FamilyMember> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRelationship = CursorUtil.getColumnIndexOrThrow(_cursor, "relationship");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfAnniversaryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "anniversaryDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<FamilyMember> _result = new ArrayList<FamilyMember>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FamilyMember _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRelationship;
            _tmpRelationship = _cursor.getString(_cursorIndexOfRelationship);
            final Long _tmpDateOfBirth;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmpDateOfBirth = null;
            } else {
              _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            final Long _tmpAnniversaryDate;
            if (_cursor.isNull(_cursorIndexOfAnniversaryDate)) {
              _tmpAnniversaryDate = null;
            } else {
              _tmpAnniversaryDate = _cursor.getLong(_cursorIndexOfAnniversaryDate);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new FamilyMember(_tmpId,_tmpCustomerId,_tmpName,_tmpRelationship,_tmpDateOfBirth,_tmpAnniversaryDate,_tmpCreatedAt);
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
