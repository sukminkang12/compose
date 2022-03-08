package com.sukminkang.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sukminkang.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.launch
import kotlin.math.exp

class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ComposeTheme {
//                MyApp()
//            }
//        }
//    }
}

//@Composable
//private fun MyApp() {
//    ConstraintLayoutContent()
//}

@Composable
fun Greetings(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            CardContent(name = name)
        }
    }
}


@Composable
private fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(12.dp)) {
            Text(text = "Hello, ")
            Text(text = name,
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.ExtraBold
            ))
            if (expanded) {
                Text(text = ("Composem ipsum color sit lazy, " +
                        "padding theme elit, sed do bouncy. ").repeat(4),)
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(id = R.string.show_less)
                } else {
                    stringResource(id = R.string.show_more)
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String) {
    var expanded by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(targetValue = if (expanded)
        48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) 
        {
            Text("Welcome to the Basics Codelab!")
            Button(onClick = onContinueClicked
            , modifier = Modifier.padding(vertical = 24.dp)) {
                Text(text = "Continue")
            }
        }
    }
}

@Composable
fun PhotographerCard() {
    Row {
        Surface(modifier = Modifier.size(50.dp),
        shape = CircleShape, color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)) {
        }
        Column (modifier = Modifier
            .padding(start = 8.dp)
            .align(Alignment.CenterVertically)){
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "LayoutsCodelab")
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }
            })
        }
    )
    { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = "Hi there!")
        Text(text = "Thanks for going through the Layouts codelab")
    }
}

@Composable
fun SimpleList() {
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)){
        repeat(100) {
            Text(text = "Item $it")
        }
    }
}

@Composable
fun LazyList() {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            Text("Item #$it")
        }
    }
}

//@Composable
//fun ImageList() {
//    val scrollState = rememberLazyListState()
//
//    LazyColumn(state = scrollState) {
//        items(100) {
//            ImageListItem(it)
//        }
//    }
//}

//@Composable
//fun ImageListItem(index:Int) {
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        Image(
//            painter = rememberImagePainter(data =
//            "https://developer.android.com/images/brand/Android_Robot.png"
//            ),
//            contentDescription = "Android Logo",
//            modifier = Modifier.size(50.dp)
//        )
//        Spacer(modifier = Modifier.width(10.dp))
//        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
//    }
//}
//
//@Composable
//fun ScrollingList() {
//    val listSize = 100
//    val scrollState = rememberLazyListState()
//    val coroutineScope = rememberCoroutineScope()
//
//    Column {
//        Row {
//            Button(onClick = {
//                coroutineScope.launch {
//                    // 0 is the first item index
//                    scrollState.animateScrollToItem(0)
//                }
//            }) {
//                Text("Scroll to the top")
//            }
//
//            Button(onClick = {
//                coroutineScope.launch {
//                    // listSize - 1 is the last index of the list
//                    scrollState.animateScrollToItem(listSize - 1)
//                }
//            }) {
//                Text("Scroll to the end")
//            }
//        }
//
//        LazyColumn(state = scrollState) {
//            items(listSize) {
//                ImageListItem(it)
//            }
//        }
//    }
//}
//
//@Composable
//fun ConstraintLayoutContent() {
//    ConstraintLayout {
//
//        val (button, text) = createRefs()
//
//        Button(
//            onClick = {  },
//            modifier = Modifier.constrainAs(button) {
//                top.linkTo(parent.top, margin = 16.dp)
//            }
//        ) {
//            Text("Button")
//        }
//
//        Text("Text", Modifier.constrainAs(text) {
//            top.linkTo(button.bottom, margin = 16.dp)
//        })
//    }
//}
//
//
//@Composable
//fun LargeConstraintLayout() {
//    ConstraintLayout {
//        val text = createRef()
//
//        val guideline = createGuidelineFromStart(fraction = 0.5f)
//        Text(
//            "This is a very very very very very very very long text",
//            Modifier.constrainAs(text) {
//                linkTo(start = guideline, end = parent.end)
//                width = Dimension.preferredWrapContent
//            }
//        )
//    }
//}
//
//@Composable
//fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
//    Row(modifier = modifier.height(IntrinsicSize.Min)) {
//        Text(
//            modifier = Modifier
//                .weight(1f)
//                .padding(start = 4.dp)
//                .wrapContentWidth(Alignment.Start),
//            text = text1
//        )
//        Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
//        Text(
//            modifier = Modifier
//                .weight(1f)
//                .padding(end = 4.dp)
//                .wrapContentWidth(Alignment.End),
//
//            text = text2
//        )
//    }
//}
//
//
//@Preview()
//@Composable
//fun DefaultPreview() {
//    ComposeTheme {
//        Surface {
//            TwoTexts(text1 = "Hi", text2 = "there")
//        }
//    }
//}