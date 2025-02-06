package com.example.test12.presentation.onboarding

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.data.local_data_source.Onboarding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingViewModel(private val db: AppDatabase): ScreenModel {

    init{
        screenModelScope.launch(Dispatchers.IO) {
            val result = db.OnBoardingDao().getNumber()
            if(result == 0){
                db.OnBoardingDao().insert(Onboarding(number = 1))
            }
        }

    }
}