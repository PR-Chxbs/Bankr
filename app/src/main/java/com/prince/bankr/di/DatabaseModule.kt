package com.prince.bankr.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.prince.bankr.data.local.BankrDatabase
import com.prince.bankr.data.local.dao.*
import com.prince.bankr.data.repository.*
import com.prince.bankr.gamification.BadgeManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): BankrDatabase {
        return Room.databaseBuilder(
            app,
            BankrDatabase::class.java,
            "bankr_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides fun provideAccountDao(db: BankrDatabase): AccountDao = db.accountDao()
    @Provides fun provideUserDao(db: BankrDatabase): UserDao = db.userDao()
    @Provides fun provideTransactionDao(db: BankrDatabase): TransactionDao = db.transactionDao()
    @Provides fun provideCategoryDao(db: BankrDatabase): CategoryDao = db.categoryDao()
    @Provides fun provideBudgetDao(db: BankrDatabase): BudgetGoalDao = db.budgetGoalDao()
    @Provides fun provideBadgeDao(db: BankrDatabase): BadgeDao = db.badgeDao()

    @Provides fun provideAccountRepo(dao: AccountDao): AccountRepository = AccountRepository(dao)
    @Provides fun provideUserRepo(dao: UserDao): UserRepository = UserRepository(dao)
    @Provides fun provideTransactionRepo(dao: TransactionDao): TransactionRepository = TransactionRepository(dao)
    @Provides fun provideCategoryRepo(dao: CategoryDao): CategoryRepository = CategoryRepository(dao)
    @Provides fun provideBudgetRepo(dao: BudgetGoalDao): BudgetGoalRepository = BudgetGoalRepository(dao)
    @Provides fun provideBadgeRepo(dao: BadgeDao): BadgeRepository = BadgeRepository(dao)

    @Provides fun provideBadgeManager(badgeRepo: BadgeRepository, budgetRepo: BudgetGoalRepository, transactionRepo: TransactionRepository): BadgeManager = BadgeManager(badgeRepo, budgetRepo, transactionRepo)

}
