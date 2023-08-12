package com.rwmobi.xlaunchericons

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rwmobi.xlaunchericons.ui.theme.XLauncherIconsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XLauncherIconsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF161D26)),
                        icons = gregAppIcons,
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
    icons: List<AppIcon>,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(48.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Subscribe to greg for only $1",
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
            icons.forEach {
                AppIconOption(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(54.dp),
                    appIcon = it,
                )
            }
        }
    }
}

@Composable
private fun AppIconOption(
    modifier: Modifier = Modifier,
    appIcon: AppIcon,
) {
    val context = LocalContext.current

    Image(
        modifier = modifier
            .drawBehind {
                drawCircle(color = Color(0xFF536972))
            }
            .clip(CircleShape)
            .clickable(
                enabled = true,
                onClick = {
                    setIcon(
                        context = context,
                        componentName = appIcon.component,
                    )
                },
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
        MainScreen(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF161D26)),
            icons = gregAppIcons,
        )
    }
}

private fun setIcon(context: Context, componentName: String) {
    val packageManager = context.packageManager

    gregAppIcons.filter {
        it.component != componentName
    }.forEach {
        packageManager.setComponentEnabledSetting(
            ComponentName(context, it.component),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP,
        )
    }

    packageManager.setComponentEnabledSetting(
        ComponentName(context, componentName),
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP,
    )
}
