package com.example.newsapplication.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.newsapplication.R
import com.example.newsapplication.presentation.Dimen
import com.example.newsapplication.presentation.Dimen.MediumPadding2
import com.example.newsapplication.presentation.onboarding.Page

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page:Page
) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            painter = painterResource(id = page.image), contentDescription = null,
            contentScale = ContentScale.Crop
            )
        Spacer(modifier = Modifier.height(Dimen.MediumPadding1))
        Text(text = page.title,
            modifier = Modifier.padding(horizontal = Dimen.MediumPadding2),
            style = MaterialTheme.typography.displaySmall.copy(
                color = colorResource(id = R.color.text_medium)
            )
            )
        Text(
            modifier = Modifier.padding(horizontal = MediumPadding2),
            text = page.description,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium)
        )
        
    }


}