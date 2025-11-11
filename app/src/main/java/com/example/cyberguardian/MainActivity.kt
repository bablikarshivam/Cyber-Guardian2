package com.example.cyberguardian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // Correctly imported for .clickable modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cyberguardian.ui.theme.CyberGuardianTheme // Assuming theme is here

// KEEP package name in sync with your project

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CyberGuardianTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppRoot()
                }
            }
        }
    }
}

/* ----------------- App Root & Navigation ----------------- */
@Composable
fun AppRoot() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "login") {
        composable("login") { LoginScreen(onContinue = { nav.navigate("home") }) }
        composable("home") { HomeScreen(onOpenScanner = { nav.navigate("scanner") }, onOpenAwareness = { nav.navigate("awareness") }, onOpenPhishing = { nav.navigate("phishing") }) }
        composable("scanner") { ScannerScreen(onOpenAwareness = { nav.navigate("awareness") }, onOpenPhishing = { nav.navigate("phishing") }) }
        composable("awareness") { AwarenessScreen() }
        composable("phishing") { PhishingScreen() }
    }
}

/* ----------------- App Top Bar (Example) ----------------- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(showAvatar: Boolean) {
    TopAppBar(
        title = { Text("Cyber Guardian") },
        actions = {
            if (showAvatar) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Placeholder
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

/* ----------------- Login Screen ----------------- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onContinue: () -> Unit) {
    Scaffold(topBar = { AppTopBar(showAvatar = false) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "logo",
                modifier = Modifier.size(180.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("Welcome to", fontSize = 20.sp, color = Color(0xFF3F2E7D))
            Text("Cyber Guardian", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F2E7D))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Enter your name to continue", color = Color.Gray)
            Spacer(modifier = Modifier.height(18.dp))
            var name by remember { mutableStateOf("") }
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Your Name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF9B7BFF),
                    unfocusedBorderColor = Color(0xFFE6DEFF)
                )
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C52D8))
            ) {
                Text("Continue", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

/* ----------------- Home Screen ----------------- */
@Composable
fun HomeScreen(onOpenScanner: () -> Unit, onOpenAwareness: () -> Unit, onOpenPhishing: () -> Unit) {
    Scaffold(topBar = { AppTopBar(showAvatar = true) }) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                Text("Security Overview", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF3F2E7D), modifier = Modifier.padding(vertical = 8.dp))
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFFC94A), shape = RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Column {
                                    Text("Current Protection level", color = Color.Black.copy(alpha = 0.7f), fontSize = 12.sp)
                                    Text("Action required", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                }
                                Text("1 hour ago", fontSize = 12.sp, color = Color.Black.copy(alpha = 0.7f))
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text("Activity Snapshot", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                        Column {
                            SnapshotRow("App Scanned", "18")
                            SnapshotRow("Threats blocked", "6")
                            SnapshotRow("New Awareness Tips", "5")
                            SnapshotRow("SOS Alerts", "1")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                // Quick action grid
                Row(modifier = Modifier.fillMaxWidth()) {
                    QuickActionCard("Phishing Links", R.drawable.ic_launcher_background, onClick = onOpenPhishing, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(10.dp))
                    QuickActionCard("Mobile App Scanner", R.drawable.ic_launcher_background, onClick = onOpenScanner, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    QuickActionCard("Cyber Awareness", R.drawable.ic_launcher_background, onClick = onOpenAwareness, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(10.dp))
                    QuickActionCard("SOS Alert", R.drawable.ic_launcher_background, onClick = {}, modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun SnapshotRow(title: String, count: String) {
    Card(shape = RoundedCornerShape(10.dp), modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(title)
            Text(count, fontWeight = FontWeight.Bold, color = Color(0xFFBE2C2C))
        }
    }
}

@Composable
private fun QuickActionCard(text: String, drawableRes: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(shape = RoundedCornerShape(12.dp), modifier = modifier.clickable { onClick() }, elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = drawableRes), contentDescription = text, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, textAlign = TextAlign.Center, fontSize = 14.sp)
        }
    }
}

/* ----------------- Scanner Screen ----------------- */
@Composable
fun ScannerScreen(onOpenAwareness: () -> Unit, onOpenPhishing: () -> Unit) {
    Scaffold(topBar = { AppTopBar(showAvatar = true) }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            Text("APP SCANNER", fontWeight = FontWeight.SemiBold, color = Color(0xFF3F2E7D), modifier = Modifier.padding(top = 8.dp))
            Spacer(modifier = Modifier.height(14.dp))

            // Circular percent indicator (custom)
            CircularScanProgress(percent = 75)

            Spacer(modifier = Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                SmallInfoCard("Permission Risk", "HIGH RISK: 3")
                SmallInfoCard("Last Scan", "Today, 10:00 AM")
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Apps with Access", fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            Card(shape = RoundedCornerShape(10.dp), modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
                // list placeholder
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    item { AppRow("Whatsapp") }
                    item { AppRow("Instagram") }
                    item { AppRow("TikTok") }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            Button(onClick = onOpenAwareness, modifier = Modifier
                .fillMaxWidth()
                .height(52.dp), shape = RoundedCornerShape(28.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C52D8))) {
                Text("Open Awareness", color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = onOpenPhishing, modifier = Modifier
                .fillMaxWidth()
                .height(48.dp), shape = RoundedCornerShape(24.dp)) {
                Text("Manual Link Scanner")
            }
        }
    }
}

@Composable
private fun AppRow(name: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(name, modifier = Modifier.weight(1f))
        Button(onClick = {}, shape = RoundedCornerShape(20.dp), modifier = Modifier.height(36.dp)) {
            Text("View", fontSize = 14.sp)
        }
    }
}

@Composable
private fun SmallInfoCard(title: String, value: String) {
    Card(shape = RoundedCornerShape(10.dp), modifier = Modifier.widthIn(min = 140.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CircularScanProgress(percent: Int) {
    // Animated float
    val animated = animateFloatAsState(targetValue = percent / 100f, animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing), label = "")
    val size = 180.dp
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size)) {
        // background circle
        Canvas(modifier = Modifier.size(size)) {
            val stroke = Stroke(width = 18f, cap = StrokeCap.Round)
            drawCircle(color = Color(0xFFDDE6F6), style = stroke)
            val sweep = animated.value * 360f
            drawArc(
                brush = Brush.sweepGradient(listOf(Color(0xFF4B6CB7), Color(0xFF00C6A7))),
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                style = stroke
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${percent}%", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F2E7D))
            Text("SCANNED", color = Color.Gray)
            Text("3 APPS FOUND", color = Color.LightGray, fontSize = 12.sp)
        }
    }
}

/* ----------------- Awareness Screen ----------------- */
@Composable
fun AwarenessScreen() {
    Scaffold(topBar = { AppTopBar(showAvatar = true) }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {
            Text("CYBER AWARENESS", fontWeight = FontWeight.SemiBold, color = Color(0xFF3F2E7D), modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(10.dp))
            Text("Stay Safe Online", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F2E7D))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Learn about scams & protect yourself.", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            AwarenessItem("Common Scams", "Phishing, OTP Fraud")
            AwarenessItem("Secure Your Accounts", "")
            AwarenessItem("Recognise Fake News", "")
            AwarenessItem("Safe Messaging Habits", "")

            Spacer(modifier = Modifier.weight(1f))
            Text("Knowledge is your best defense.", color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun AwarenessItem(title: String, subtitle: String) {
    Card(shape = RoundedCornerShape(10.dp), modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEFF4FF)))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold)
                if (subtitle.isNotEmpty()) Text(subtitle, color = Color.Gray, fontSize = 12.sp)
            }
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "More", tint = Color(0xFF6C52D8))
            }
        }
    }
}

/* ----------------- Phishing Screen ----------------- */
@Composable
fun PhishingScreen() {
    Scaffold(topBar = { AppTopBar(showAvatar = true) }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {
            Text("PHISHING LINKS", fontWeight = FontWeight.SemiBold, color = Color(0xFF3F2E7D), modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(14.dp))
            Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.weight(1f)) {
                        Text("Scan SMS for\nPhishing Links", fontWeight = FontWeight.SemiBold)
                    }
                    Switch(checked = true, onCheckedChange = {})
                }
            }
        }
    }
}
