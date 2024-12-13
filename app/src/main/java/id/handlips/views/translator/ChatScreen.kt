package id.handlips.views.translator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.R
import id.handlips.component.button.LongButton
import id.handlips.component.dialog.DialogError
import id.handlips.component.dialog.DialogSuccess
import id.handlips.component.textfield.GeneralTextField
import id.handlips.ui.theme.HandlipsTheme
import id.handlips.ui.theme.White

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    onCreateChat: () -> Unit,
) {
    var communicationTitle by remember { mutableStateOf("") }
    var dialogError by remember { mutableStateOf(false) }
    var dialogSuccess by remember { mutableStateOf(false) }
    var textError by remember { mutableStateOf("") }
    var textSuccess by remember { mutableStateOf("") }
    if (dialogError) {
        DialogError(onDismissRequest = { dialogError = false }, textError = textError)
    }
    if (dialogSuccess) {
        DialogSuccess(onDismissRequest = onCreateChat, textSuccess = textSuccess)
    }
    HandlipsTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = White,
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(120.dp),
            ) {
                Text(text = "Communication", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.communication),
                        contentDescription = "Communication",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    GeneralTextField(
                        title = "Communication Title",
                        label = "Enter communication title",
                        value = communicationTitle,
                        onValueChange = { communicationTitle = it },
                    )
                    LongButton(text = "Create New", onClick = {
                        if(communicationTitle.isNotBlank()){
                            dialogSuccess = true
                            textSuccess = "Communication created successfully"
                        } else {
                            dialogError = true
                            textError = "Please enter communication title"
                        }
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatScreenPreview(modifier: Modifier = Modifier) {
//    ChatScreen()
}
