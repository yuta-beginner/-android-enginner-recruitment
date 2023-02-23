package com.oishikenko.android.recruitment.feature.list


import android.provider.SyncStateContract.Columns
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.oishikenko.android.recruitment.data.model.CookingRecord
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RecipeListItem(
    cookingRecord: CookingRecord
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color(220,224,224),
                shape = RoundedCornerShape(8.dp)
            )
    )
    {
        AsyncImage(
            model = cookingRecord.imageUrl,
            contentDescription = cookingRecord.comment,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(96.dp)
                //.clip(RoundedCornerShape(8.dp))
        )
        /**
         * issues#2の実装
         * 1. Columnで2カ所のテキストエリアを作る。
         * 2.
         */
        Column(
            modifier = Modifier
                .height(96.dp)
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp
                )
                .fillMaxWidth()
        ) {
            // 日本語変換したrecipeTypeを表示するテキスト部分
            Text(
                text = translateRecipeTypeToJapanese(cookingRecord.recipeType),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(51,51,51,255),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.BottomStart)
            )
            // フォーマット変換したrecordedAtを表示するテキスト部分
            Text(
                text = formatRecordedAt(cookingRecord.recordedAt),
                fontSize = 14.sp,
                color = Color(103,103,103,255),
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun PreviewRecipeListItem() {
    RecipeListItem(
        cookingRecord = CookingRecord(
            imageUrl= "",
            comment = "豚肉のコクとごぼうの香り、野菜の甘みで奥行きのある味わい。",
            recipeType = "soup",
            recordedAt = "2018-05-01 17:57:31"
        )
    )
}

/**
 * issues#2の実装
 * 取得したrecipeTypeの文字列に応じて日本語変換するメソッド
 */
fun translateRecipeTypeToJapanese(recipeType: String):String{
    var convertedRecipeType:String = recipeType
    if (recipeType.equals("soup")){
        convertedRecipeType = "スープ"
    } else if(recipeType.equals("main_dish")) {
        convertedRecipeType = "主菜/主食"
    } else {
        convertedRecipeType = "副菜"
    }
    return convertedRecipeType
}

/**
 * issues#2の実装
 * 取得したrecordedAtの文字列に応じてyyyy/MM/dd hh:mmのフォーマットに変換するメソッド
 */
fun formatRecordedAt(recordedAt: String):String {
    val recordedAtDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    var formattedDate:Date = recordedAtDate.parse(recordedAt)
    val viewFormat = SimpleDateFormat("yyyy/MM/dd hh:mm")
    var strFormattedDate:String = viewFormat.format(formattedDate)
    return strFormattedDate
}