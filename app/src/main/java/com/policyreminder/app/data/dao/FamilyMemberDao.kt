package com.policyreminder.app.data.dao

import androidx.room.*
import com.policyreminder.app.data.model.FamilyMember
import kotlinx.coroutines.flow.Flow

@Dao
interface FamilyMemberDao {
    
    @Query("SELECT * FROM family_members WHERE customerId = :customerId ORDER BY name ASC")
    fun getFamilyMembersByCustomerId(customerId: Long): Flow<List<FamilyMember>>
    
    @Query("SELECT * FROM family_members WHERE id = :memberId")
    fun getFamilyMemberById(memberId: Long): Flow<FamilyMember?>
    
    @Query("SELECT * FROM family_members WHERE dateOfBirth IS NOT NULL")
    fun getAllFamilyMembersWithBirthdays(): Flow<List<FamilyMember>>
    
    @Query("SELECT * FROM family_members WHERE anniversaryDate IS NOT NULL")
    fun getAllFamilyMembersWithAnniversaries(): Flow<List<FamilyMember>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFamilyMember(member: FamilyMember): Long
    
    @Update
    suspend fun updateFamilyMember(member: FamilyMember)
    
    @Delete
    suspend fun deleteFamilyMember(member: FamilyMember)
    
    @Query("DELETE FROM family_members WHERE id = :memberId")
    suspend fun deleteFamilyMemberById(memberId: Long)
}
