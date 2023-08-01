package com.rwmobi.xlaunchericons

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(modifier = modifier.background(color = Color(0xFF161D26))) {

        Spacer(modifier = Modifier.size(48.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Subscribe to greg for only $1",
            color = Color.LightGray,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(48.dp))

        Row(

        ) {


            Icon(
                modifier = Modifier.clickable(enabled = true, onClick = {
                    setIcon(
                        context = context,
                        componentName = "com.rwmobi.xlaunchericons.MainActivityA"
                    )
                }),
                painter = painterResource(id = R.drawable.ic_launcher_a_foreground),
                contentDescription = null
            )

            Spacer(modifier = Modifier.weight(1.0f))

            Icon(
                modifier = Modifier.clickable(enabled = true, onClick = {
                    setIcon(
                        context = context,
                        componentName = "com.rwmobi.xlaunchericons.MainActivityB"
                    )
                }),
                painter = painterResource(id = R.drawable.ic_launcher_b_foreground),
                contentDescription = null
            )

            Spacer(modifier = Modifier.weight(1.0f))

            Icon(
                modifier = Modifier.clickable(enabled = true, onClick = {
                    setIcon(
                        context = context,
                        componentName = "com.rwmobi.xlaunchericons.MainActivityC"
                    )
                }),
                painter = painterResource(id = R.drawable.ic_launcher_c_foreground),
                contentDescription = null
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun GreetingPreview() {
    XLauncherIconsTheme {
        MainScreen(modifier = Modifier.fillMaxWidth())
    }
}

private fun setIcon(context: Context, componentName: String) {
    val packageManager = context.packageManager

    // If there's a currently enabled component, disable it
    val components = listOf(
        "com.rwmobi.xlaunchericons.MainActivity",
        "com.rwmobi.xlaunchericons.MainActivityA",
        "com.rwmobi.xlaunchericons.MainActivityB",
        "com.rwmobi.xlaunchericons.MainActivityC"
    )
    components.forEach() {
        packageManager.setComponentEnabledSetting(
            ComponentName(context, it),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    packageManager.setComponentEnabledSetting(
        ComponentName(context, componentName),
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )
}
