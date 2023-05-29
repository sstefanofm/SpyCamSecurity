package com.example.spycamsecurity.ui.newinstance

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.spycamsecurity.common.toLocalizedResource
import com.example.spycamsecurity.domain.UserType
import com.example.spycamsecurity.ui.*
import com.example.spycamsecurity.ui.components.AppToolbar
import com.example.spycamsecurity.ui.components.LoadingScreen

private const val TITLE = "New Instance"

@Composable
fun NewInstanceScreen(
    onEventHandler: (NewInstanceEvent) -> Unit,
    viewModel: NewInstanceViewModel
) {
    val contentStateTransition = remember {
        MutableTransitionState(
            true
        )
    }

    val transition = updateTransition(contentStateTransition)

    val loadingAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) }
    ) {
        if (it) 1f else 0f
    }

    val mainAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) }
    ) {
            if (!it) 1f else 0f
    }

    viewModel.subLoadingState = {
        contentStateTransition.targetState = it
    }

     Box(
         Modifier
             .fillMaxSize()
             .background(MaterialTheme.colors.primary)
        ) {
            Box(Modifier.alpha(loadingAlpha)) { LoadingScreen() }
            if (!contentStateTransition.currentState) Box(Modifier.alpha(mainAlpha)) {
                NewInstanceContent(
                    onEventHandler,
                    viewModel
                )
            }
        }
    }

@Composable
fun NewInstanceContent(
    onEventHandler: (NewInstanceEvent) -> Unit,
    viewModel: NewInstanceViewModel
) {
    Surface(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        ConstraintLayout(Modifier.background(MaterialTheme.colors.primary)) {
            val (toolbar,
                sizeDropdown,
                diffDropdown,
                stats
            ) = createRefs()

            AppToolbar(
                modifier = Modifier.constrainAs(toolbar) {
                    top.linkTo(parent.top)
                },
                title = TITLE,
            ) { DoneIcon(onEventHandler = onEventHandler) }

            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 32.dp)
                    .constrainAs(sizeDropdown) {
                        top.linkTo(toolbar.bottom)
                    }) {
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .constrainAs(diffDropdown) {
                        top.linkTo(sizeDropdown.bottom)
                    }) {
                DropdownWithTitle(
                    onEventHandler,
                    stringResource(R.string.userType_title),
                    viewModel.settingsState.userType.ordinal,
                    listOf(
                        UserType.SERVER,
                        UserType.CLIENT
                    )
                )
            }
        }
    }
}

@Composable
fun DropdownWithTitle(
    onEventHandler: (NewInstanceEvent) -> Unit,
    titleText: String,
    initialIndex: Int,
    items: List<Any>,
) {
    val isSizeMenu = items[0] is String

    var showMenu by remember { mutableStateOf(false) }
    var menuIndex by remember {
        mutableStateOf(
            initialIndex
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = titleText,
            style = newInstanceSubtitle.copy(color = MaterialTheme.colors.secondary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp),
        )

        Row(
            Modifier.clickable(
                onClick = { showMenu = true }
            )
        ) {
            Text(
                text = if (isSizeMenu) items[menuIndex] as String
                else stringResource(id = (items[menuIndex] as UserType).toLocalizedResource),
                style = newInstanceSubtitle.copy(
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .wrapContentSize()
            )

            Icon(
                contentDescription = stringResource(R.string.dropdown),
                imageVector = Icons.Outlined.ArrowDropDown,
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .size(48.dp)
                    .rotate(
                        animateFloatAsState(
                            if (!showMenu) 0f else 180f,
                        ).value
                    )
                    .align(Alignment.CenterVertically)
            )

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {
                items.forEachIndexed { index, _ ->
                    DropdownMenuItem(
                        onClick = {
                            menuIndex = index
                            showMenu = false
                            onEventHandler.invoke(
                                //the first.toString.toInt is so we don't get the ASCII value
                                NewInstanceEvent.OnUserTypeChanged((items[index] as UserType))
                            )
                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .background(MaterialTheme.colors.surface)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isSizeMenu) items[index] as String
                                else stringResource(id = (items[index] as UserType).toLocalizedResource),
                                style = dropdownText(MaterialTheme.colors.primaryVariant),
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            if (!isSizeMenu) {
                                (0..index).forEach {
                                    Icon(
                                        contentDescription = stringResource(R.string.userType),
                                        imageVector = Icons.Filled.Star,
                                        tint = MaterialTheme.colors.primaryVariant,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotWatchingIcon(onEventHandler: (NewInstanceEvent) -> Unit) {
    Icon(
        imageVector = Icons.Filled.Done,
        tint = if (MaterialTheme.colors.isLight) textColorLight else textColorDark,
        contentDescription = null,
        modifier = Modifier
            .clickable(onClick = {
                onEventHandler.invoke(
                    NewInstanceEvent.OnDonePressed
                )
            })
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .height(36.dp),
    )
}
