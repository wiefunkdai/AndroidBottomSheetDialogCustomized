/*
 * AndroidBottomSheetDialogCustumized by Stephanus Dai
 * @fullname : Stephanus Bagus Saputra
 *             ( 戴 Dai 偉 Wie 峯 Funk )
 * @email    : wiefunk@stephanusdai.web.id
 * @contact  : http://t.me/wiefunkdai
 * @support  : http://opencollective.com/wiefunkdai
 * @weblink  : http://www.stephanusdai.web.id
 * Copyright (c) ID 2023 Stephanus Bagus Saputra. All rights reserved.
 * Terms of the following https://stephanusdai.web.id/p/license.html
 */

package id.web.stephanusdai.bottomsheetdialogcustomized

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var bottomSheetDialog: BottomSheetUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomSheetDialog = BottomSheetUtil(this, supportFragmentManager)
        val btnMessage = findViewById<Button>(R.id.button)
        btnMessage.setOnClickListener({
            bottomSheetDialog.show("Information!", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse pretium purus dignissim arcu facilisis malesuada. Suspendisse a erat congue, porta justo sed, rutrum nisi.")
        })

    }
}