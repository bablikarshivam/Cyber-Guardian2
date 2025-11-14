package com.example.cyberguardian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cyberguardian.ui.theme.CyberGuardianTheme
import com.example.cyberguardian.ui.theme.jersey25

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CyberGuardianTheme {
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

@Composable
fun AppRoot() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "login") {
        composable("login") { LoginScreen(onContinue = { nav.navigate("home") }) }
        composable("home") { HomeScreen(onOpenScanner = { nav.navigate("scanner") }, onOpenAwareness = { nav.navigate("awareness") }, onOpenPhishing = { nav.navigate("phishing") }) }
        composable("scanner") { ScannerScreen(navController = nav) }
        composable("awareness") { AwarenessScreen() }
        composable("phishing") { PhishingScreen() }
        composable("permission_access") { PermissionAccessScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(showAvatar: Boolean) {
    CenterAlignedTopAppBar(
        title = { Text("CYBER GUARDIAN", fontFamily = jersey25, fontWeight = FontWeight.Bold, color = Color(0xFF3F2E7D), fontSize = 24.sp) },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.cyber_guardian_logo),
                contentDescription = "Cyber Guardian Logo",
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 8.dp)
            )
        },
        actions = {
            if (showAvatar) {
                Image(
                    painter = painterResource(id = R.drawable.cyber_guardian_logo),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .padding(end = 8.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

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
                painter = painterResource(id = R.drawable.cyber_guardian_logo),
                contentDescription = "Cyber Guardian Logo",
                modifier = Modifier.size(180.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("Welcome to", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F2E7D))
            Text("CYBER GUARDIAN", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F2E7D))
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

@Composable
fun HomeScreen(onOpenScanner: () -> Unit, onOpenAwareness: () -> Unit, onOpenPhishing: () -> Unit) {
    Scaffold(
        topBar = { AppTopBar(showAvatar = true) },
        bottomBar = { AppFooter() }
    ) { padding ->
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
                            SnapshotRow("App Scanned", "18", R.drawable.cyber_guardian_logo)
                            SnapshotRow("Threats blocked", "6", R.drawable.threats_blocked_logo)
                            SnapshotRow("New Awareness Tips", "5", R.drawable.new_awareness_tips_logo)
                            SnapshotRow("SOS Alerts", "1", R.drawable.sos_alert_logo)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    QuickActionCard("Phishing Links", icon = { Image(painter = painterResource(id = R.drawable.phishing_links_logo), contentDescription = "Phishing Links", modifier = Modifier.size(48.dp)) }, onClick = onOpenPhishing, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(10.dp))
                    QuickActionCard("Mobile App Scanner", icon = { Image(painter = painterResource(id = R.drawable.cyber_guardian_logo), contentDescription = "Mobile App Scanner", modifier = Modifier.size(48.dp)) }, onClick = onOpenScanner, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    QuickActionCard("Cyber Awareness", icon = { Image(painter = painterResource(id = R.drawable.cyber_awareness_logo), contentDescription = "Cyber Awareness", modifier = Modifier.size(48.dp)) }, onClick = onOpenAwareness, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(10.dp))
                    QuickActionCard("SOS Alert", icon = { Image(painter = painterResource(id = R.drawable.sos_alert_logo), contentDescription = "SOS Alert", modifier = Modifier.size(48.dp)) }, onClick = {}, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun SnapshotRow(title: String, count: String, iconRes: Int) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = iconRes), contentDescription = title, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(title)
            Spacer(Modifier.weight(1f))
            Text(count, fontWeight = FontWeight.Bold, color = Color(0xFFBE2C2C))
        }
    }
}

@Composable
private fun QuickActionCard(text: String, icon: @Composable () -> Unit, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            icon()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, textAlign = TextAlign.Center, fontSize = 14.sp)
        }
    }
}

@Composable
fun ScannerScreen(navController: NavController) {
    Scaffold(topBar = { AppTopBar(showAvatar = true) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("APP SCANNER", fontWeight = FontWeight.SemiBold, color = Color(0xFF3F2E7D), modifier = Modifier.padding(top = 8.dp))
            Spacer(modifier = Modifier.height(14.dp))
            IconButton(onClick = { navController.navigate("permission_access") }, modifier = Modifier.size(180.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.power_button),
                    contentDescription = "Scan",
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                SmallInfoCard("Permission Risk", "HIGH RISK: 3")
                SmallInfoCard("Last Scan", "Today, 10:00 AM")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Apps with Access", fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    item { AppRow("Whatsapp") }
                    item { AppRow("Instagram") }
                    item { AppRow("TikTok") }
                }
            }
        }
    }
}

@Composable
private fun AppRow(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, modifier = Modifier.weight(1f))
        Button(onClick = {}, shape = RoundedCornerShape(20.dp), modifier = Modifier.height(36.dp)) {
            Text("View", fontSize = 14.sp)
        }
    }
}

@Composable
private fun SmallInfoCard(title: String, value: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.widthIn(min = 140.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AwarenessScreen() {
    Scaffold(topBar = { AppTopBar(showAvatar = true) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("CYBER AWARENESS", fontWeight = FontWeight.SemiBold, color = Color(0xFF3F2E7D), modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(10.dp))
            Text("Stay Safe Online", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F2E7D))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Learn about scams & protect yourself.", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            AwarenessItem("Common Scams", "Phishing, OTP Fraud", R.drawable.common_scam)
            AwarenessItem("Secure Your Accounts", "", R.drawable.secure_your_account)
            AwarenessItem("Recognise Fake News", "", R.drawable.recognise_fake_news)
            AwarenessItem("Safe Messaging Habits", "", R.drawable.safe_messaging_habit)

            Spacer(modifier = Modifier.weight(1f))
            Text("Knowledge is your best defense.", color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun AwarenessItem(title: String, subtitle: String, iconRes: Int) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = iconRes), contentDescription = title, modifier = Modifier.size(44.dp).clip(RoundedCornerShape(8.dp)))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold)
                if (subtitle.isNotEmpty()) Text(subtitle, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhishingScreen() {
    var url by remember { mutableStateOf("") }
    var isUnsecure by remember { mutableStateOf<Boolean?>(null) }

    Scaffold(
        topBar = { AppTopBar(showAvatar = true) },
        bottomBar = { AppFooter() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("PHISHING LINKS", fontWeight = FontWeight.SemiBold, color = Color(0xFF3F2E7D))
            Spacer(modifier = Modifier.height(14.dp))

            Card(shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.phishing_links_logo),
                        contentDescription = "Scan SMS",
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Scan SMS for Phishing Links", fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    Switch(checked = true, onCheckedChange = {})
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Manual Link Check", fontWeight = FontWeight.SemiBold, color = Color(0xFF3F2E7D))
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = url,
                onValueChange = { url = it; isUnsecure = null },
                placeholder = { Text("Enter URL to scan...") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { isUnsecure = url.contains("unsecure", ignoreCase = true) }) {
                        Image(painter = painterResource(id = R.drawable.cyber_guardian_logo), contentDescription = "Scan", modifier = Modifier.size(24.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isUnsecure != null) {
                val message = if (isUnsecure == true) "LINK IS UNSECURE" else "LINK IS SECURE"
                val color = if (isUnsecure == true) Color(0xFFBE2C2C) else Color(0xFF00C6A7)
                Text(message, color = color, fontWeight = FontWeight.Bold)
                if (isUnsecure == true) {
                    Text(url, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { isUnsecure = url.contains("unsecure", ignoreCase = true) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C52D8))
            ) {
                Text("SCAN", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun AppFooter() {
    val footerColor = Brush.verticalGradient(
        colors = listOf(Color(0xFF00008B), Color(0xFF00008B).copy(alpha = 0.7f)))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(0f, size.height * 0.3f)
                quadraticTo(
                    size.width / 2, -size.height * 0.3f,
                    size.width, size.height * 0.3f
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(
                path = path,
                brush = footerColor
            )
        }
        Text(
            text = "Real-time Protection By CyberGuardian",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 30.dp),
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PermissionAccessScreen() {
    Scaffold(topBar = { AppTopBar(showAvatar = true) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.cyber_guardian_logo), contentDescription = "Cyber Guardian Logo", modifier = Modifier.size(180.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text("CYBER GUARDIAN", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color(0xFF3F2E7D))
            Spacer(modifier = Modifier.height(14.dp))
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                item { PermissionRow("Camera") }
                item { PermissionRow("Storage") }
                item { PermissionRow("Location") }
            }
        }
    }
}

@Composable
private fun PermissionRow(name: String) {
    var isChecked by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, modifier = Modifier.weight(1f))
        Switch(checked = isChecked, onCheckedChange = { isChecked = it })
    }
}
