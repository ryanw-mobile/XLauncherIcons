package com.rwmobi.xlaunchericons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rwmobi.xlaunchericons.ui.theme.XLauncherIconsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(
                    scrim = Color.Transparent.toArgb(),
                ),
                navigationBarStyle = SystemBarStyle.dark(
                    scrim = Color.Transparent.toArgb(),
                ),
            )

            val iconManager = remember { IconManager(this) }
            val viewModel: MainViewModel = viewModel(
                factory = MainViewModel.provideFactory(iconManager),
            )

            XLauncherIconsTheme {
                Surface(
                    modifier = Modifier
                        .background(Color(0xFF161D26))
                        .fillMaxSize()
                        .safeContentPadding(),
                    color = Color(0xFF161D26),
                ) {
                    val activeIconComponent by viewModel.activeIconComponent.collectAsState()

                    MainScreen(
                        modifier = Modifier.fillMaxWidth(),
                        activeIconComponent = activeIconComponent,
                        onIconClick = { viewModel.setIcon(it) },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    activeIconComponent: String?,
    onIconClick: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(48.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.subscribe_heading),
            color = Color.LightGray,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.size(48.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            gregAppIcons.forEach {
                AppIconOption(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(54.dp),
                    appIcon = it,
                    isActive = it.component == activeIconComponent,
                    onClick = { onIconClick(it.component) },
                )
            }
        }
    }
}

@Composable
private fun AppIconOption(
    modifier: Modifier = Modifier,
    appIcon: AppIcon,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    Image(
        modifier = modifier
            .testTag("AppIcon_${appIcon.component}")
            .drawBehind {
                drawCircle(color = Color(0xFF536972))
            }
            .then(
                if (isActive) {
                    Modifier.border(
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        shape = CircleShape,
                    )
                } else {
                    Modifier
                },
            )
            .clip(CircleShape)
            .clickable(
                enabled = true,
                onClick = onClick,
            )
            .padding(all = 4.dp),
        painter = painterResource(id = appIcon.foregroundResource),
        contentDescription = null,
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun GreetingPreview() {
    XLauncherIconsTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF161D26)),
            color = Color(0xFF161D26),
        ) {
            MainScreen(
                modifier = Modifier.fillMaxWidth(),
                activeIconComponent = "com.rwmobi.xlaunchericons.MainActivityA",
                onIconClick = {},
            )
        }
    }
}
