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

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.system.OsConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetUtil {
    private val TAG: String = "BottomSheetDialogCustomized"
    private var mContext: Context
    @Suppress("DEPRECATION")
    private var mManager: FragmentManager
    private var mDefaultResIcon: String = "@android:drawable/ic_dialog_alert"
    private var mDefaultResClose: String = "@android:drawable/ic_delete"
    private var mDefaultResBackground: String = "@android:drawable/screen_background_light"

    constructor(context: Context, manager: FragmentManager) {
        mContext = context
        mManager = manager
    }

    final fun show(title: String, message: String) {
        createShowDialog(title, message, null,null, false)
    }

    final fun show(title: String, message: String, existsOnDismiss: Boolean) {
        createShowDialog(title, message, null,null, existsOnDismiss)
    }

    final fun show(title: String, message: String, icon: String?) {
        createShowDialog(title, message, icon,null,false)
    }

    final fun show(title: String, message: String, icon: String?, existsOnDismiss: Boolean) {
        createShowDialog(title, message, icon,null, existsOnDismiss)
    }

    final fun show(title: String, message: String, icon: String?, imgBG: String?) {
        createShowDialog(title, message, icon, imgBG, false)
    }

    final fun show(title: String, message: String, icon: String?, imgBG: String?, existsOnDismiss: Boolean) {
        createShowDialog(title, message, icon, imgBG, existsOnDismiss)
    }

    private fun createShowDialog(title: String, message: String, icon: String?, imgBG: String?, forceExists: Boolean) {
        try {
            val resIconID = if (icon==null) { getResourceID(mDefaultResIcon) } else { getResourceID(icon) }
            val resBackgroundID = if (imgBG==null) { getResourceID(mDefaultResBackground) } else { getResourceID(imgBG) }
            val resCloseID = getResourceID(mDefaultResClose)
            with(BottomSheetMessageDialog()) {
                setTitle(title)
                setMessage(message)
                setIconResource(resIconID)
                setBackgroundResource(resBackgroundID)
                setCloseResource(resCloseID)
                setExistsOnDismiss(forceExists)
                show(mManager, TAG)
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
        }
    }

    @SuppressLint("DiscouragedApi")
    fun getResourceID(resName: String): Int {
        try {
            val defPackage = mContext.applicationContext.packageName
            if (mContext.resources != null) {
                return mContext.resources.getIdentifier(resName, "drawable", defPackage)
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
        }
        return -1
    }

    class BottomSheetMessageDialog : BottomSheetDialogFragment()
    {
        private var mIsExistsAppOnDismiss: Boolean = false
        private lateinit var viewContainer: LinearLayout
        public lateinit var eventDismis: View.OnClickListener
        private var mTitle: String = "Warning!"
        private var mMessage: String = "A server error has occurred! Please try again in a few moments."
        private var mResIcon: Int = -1
        private var mResClose: Int = -1
        private var mResBackground: Int = -1

        private val copyright: String get() = getInfoCopyright()

        final fun setOnDismissClickListener(l: View.OnClickListener) {
            eventDismis = l
        }

        public fun isExistsOnDismiss() : Boolean
        {
            return mIsExistsAppOnDismiss
        }

        public fun setExistsOnDismiss(value: Boolean)
        {
            mIsExistsAppOnDismiss = value
        }

        fun getIconResource(): Int {
            return mResIcon
        }

        public fun setIconResource(resID: Int) {
            mResIcon = resID
        }

        fun getBackgroundResource(): Int {
            return mResBackground
        }

        public fun setBackgroundResource(resID: Int) {
            mResBackground = resID
        }

        public fun getCloseResource(): Int {
            return mResClose
        }

        public fun setCloseResource(resID: Int) {
            mResClose = resID
        }

        public fun getTitle(): String {
            return mTitle
        }

        public fun setTitle(value: String) {
            mTitle = value
        }

        public fun getMessage(): String {
            return mMessage
        }

        public fun setMessage(value: String) {
            mMessage = value
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
        {
            try {
                dialog?.let {
                    val sheet = it as BottomSheetDialog
                    sheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    sheet.behavior.peekHeight = 0
                    sheet.behavior.skipCollapsed = true
                }
            } catch (e: Exception) {
                println(e.printStackTrace())
            }
            val screenWidth = getDisplayWidth()
            val screenHeight = getDisplayHeight()

            if (screenWidth > screenHeight) {
                return createViewLandscape()
            }
            return createViewPortrait()
        }

        override fun onDismiss(dialog: DialogInterface) {
            super.onDismiss(dialog)
            @Suppress("ReplaceJavaStaticMethodWithKotlinAnalog")
            if (this.isExistsOnDismiss()) {
                System.exit(OsConstants.EXIT_SUCCESS)
            }
        }

        protected final fun onDismissClicked(view: View) {
            try {
                eventDismis.onClick(view)
            } catch (e: Exception) { }
            this.dismiss()
        }

        private fun getDisplayWidth(): Int {
            try {
                //return window.getWindowManager().getDefaultDisplay().getWidth()
                return getResources().getDisplayMetrics().widthPixels
            } catch (e: Exception) {
                return 0
            }
        }

        private fun getDisplayHeight(): Int {
            try {
                //return window.getWindowManager().getDefaultDisplay().getHeight()
                return getResources().getDisplayMetrics().heightPixels
            } catch (e: Exception) {
                return 0
            }
        }

        private fun createViewLandscape(): View {
            viewContainer = LinearLayout(context).apply {
                setOrientation(LinearLayout.VERTICAL)
                setPadding(50, 50, 50, 100)
                try {
                    dialog?.let {
                        setBackgroundColor(Color.TRANSPARENT)
                        setBackgroundResource(getBackgroundResource())
                    }
                } catch (e: Exception) {
                    setVisibility(View.INVISIBLE)
                }
            }
            try {
                val closebarWrapper = RelativeLayout(context)
                with(closebarWrapper) {
                    viewContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
                val closeButton = ImageButton(context)
                with(closeButton) {
                    closebarWrapper.addView(this)
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(10, 0, 10, 0)
                    }
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = 48
                        height = 48
                    }
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.ALIGN_PARENT_TOP)
                        addRule(RelativeLayout.ALIGN_PARENT_END)
                    }
                    try {
                        setBackgroundColor(Color.TRANSPARENT)
                        setImageResource(getCloseResource())
                    } catch (e: Exception) {
                        setVisibility(View.INVISIBLE)
                    }
                    setOnClickListener({
                        onDismissClicked(viewContainer)
                    })
                }

                val bodyWrapper = LinearLayout(context)
                with(bodyWrapper) {
                    viewContainer.addView(this)
                    this.apply {
                        setOrientation(LinearLayout.HORIZONTAL)
                        setPadding(50, 0, 50, 0)
                        with(getLayoutParams() as ViewGroup.LayoutParams) {
                            width  = ViewGroup.LayoutParams.MATCH_PARENT
                            height = ViewGroup.LayoutParams.WRAP_CONTENT
                        }
                    }
                }

                val iconWrapper = RelativeLayout(context)
                with(iconWrapper) {
                    bodyWrapper.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.WRAP_CONTENT
                        height = ViewGroup.LayoutParams.MATCH_PARENT
                    }
                }
                with(ImageView(context)) {
                    iconWrapper.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = 200
                        height = 200
                    }
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(0, 0, 0, 0)
                    }
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.CENTER_IN_PARENT)
                    }
                    try {
                        setBackgroundColor(Color.TRANSPARENT)
                        setImageResource(getIconResource())
                    } catch (e: Exception) {
                        setVisibility(View.INVISIBLE)
                    }
                }

                val contentWrapper = LinearLayout(context)
                with(contentWrapper) {
                    bodyWrapper.addView(this)
                    this.apply {
                        setOrientation(LinearLayout.VERTICAL)
                        with(getLayoutParams() as ViewGroup.LayoutParams) {
                            width  = ViewGroup.LayoutParams.MATCH_PARENT
                            height = ViewGroup.LayoutParams.WRAP_CONTENT
                        }
                        with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                            setMargins(30, 0, 0, 0)
                        }
                    }
                }

                val titleContainer = RelativeLayout(context)
                with(titleContainer) {
                    contentWrapper.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
                with(TextView(context)) {
                    titleContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.WRAP_CONTENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(0, 0, 0, 10)
                    }
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.ALIGN_PARENT_TOP)
                        addRule(RelativeLayout.ALIGN_START)
                    }
                    setText(getTitle())
                    setTextColor(Color.parseColor("#FF1744"))
                    setTextSize(24f)
                    setTypeface(Typeface.DEFAULT_BOLD)
                }
                val messageContainer = RelativeLayout(context)
                with(messageContainer) {
                    contentWrapper.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
                with(TextView(context)) {
                    messageContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.WRAP_CONTENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(0, 0, 0, 10)
                    }
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.ALIGN_PARENT_TOP)
                        addRule(RelativeLayout.ALIGN_START)
                    }
                    setText(getMessage())
                    setTextColor(Color.parseColor("#616161"))
                    setTextSize(16f)
                }
                val copyrightContainer = RelativeLayout(context)
                with(copyrightContainer) {
                    viewContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
                with(TextView(context)) {
                    copyrightContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.WRAP_CONTENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(30, 30, 30, 0)
                    }
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.ALIGN_PARENT_TOP)
                        addRule(RelativeLayout.CENTER_HORIZONTAL)
                    }
                    setText(copyright)
                    setTextColor(Color.parseColor("#616161"))
                    setTextSize(16f)
                }
            } catch (e: Exception) {
                println(e.printStackTrace())
            }
            return viewContainer
        }

        private fun createViewPortrait(): View {
            viewContainer = LinearLayout(context).apply {
                setPadding(50, 50, 50, 100)
                orientation = LinearLayout.VERTICAL
                try {
                    dialog?.let {
                        setBackgroundColor(Color.TRANSPARENT)
                        setBackgroundResource(getBackgroundResource())
                    }
                } catch (e: Exception) {
                    setVisibility(View.INVISIBLE)
                }
            }
            try {
                val contentWrapper = RelativeLayout(context)
                with(contentWrapper) {
                    viewContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
                val closeButton = ImageButton(context)
                with(closeButton) {
                    contentWrapper.addView(this)
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(10, 0, 10, 0)
                    }
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = 48
                        height = 48
                    }
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.ALIGN_PARENT_TOP)
                        addRule(RelativeLayout.ALIGN_PARENT_END)
                    }
                    try {
                        setBackgroundColor(Color.TRANSPARENT)
                        setImageResource(getCloseResource())
                    } catch (e: Exception) {
                        setVisibility(View.INVISIBLE)
                    }
                    setOnClickListener({
                        onDismissClicked(viewContainer)
                    })
                }
                with(ImageView(context)) {
                    contentWrapper.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = 200
                        height = 200
                    }
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(30, 70, 30, 10)
                    }
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.ALIGN_PARENT_TOP)
                        addRule(RelativeLayout.CENTER_HORIZONTAL)
                    }
                    try {
                        setBackgroundColor(Color.TRANSPARENT)
                        setImageResource(getIconResource())
                    } catch (e: Exception) {
                        setVisibility(View.INVISIBLE)
                    }
                }
                val titleContainer = RelativeLayout(context)
                with(titleContainer) {
                    viewContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
                with(TextView(context)) {
                    titleContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.WRAP_CONTENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(30, 10, 30, 10)
                    }
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.ALIGN_PARENT_TOP)
                        addRule(RelativeLayout.CENTER_HORIZONTAL)
                    }
                    setText(getTitle())
                    setTextColor(Color.parseColor("#FF1744"))
                    setTextSize(24f)
                    setTypeface(Typeface.DEFAULT_BOLD)
                }
                val messageContainer = RelativeLayout(context)
                with(messageContainer) {
                    viewContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
                with(TextView(context)) {
                    messageContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.WRAP_CONTENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(30, 0, 30, 50)
                    }
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.ALIGN_PARENT_TOP)
                        addRule(RelativeLayout.CENTER_HORIZONTAL)
                    }
                    setText(getMessage())
                    setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER)
                    setTextColor(Color.parseColor("#616161"))
                    setTextSize(16f)
                }
                val copyrightContainer = RelativeLayout(context)
                with(copyrightContainer) {
                    viewContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
                with(TextView(context)) {
                    copyrightContainer.addView(this)
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = ViewGroup.LayoutParams.WRAP_CONTENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(30, 0, 30, 0)
                    }
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.ALIGN_PARENT_TOP)
                        addRule(RelativeLayout.CENTER_HORIZONTAL)
                    }
                    setText(copyright)
                    setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER)
                    setTextColor(Color.parseColor("#616161"))
                    setTextSize(16f)
                }
            } catch (e: Exception) {
                println(e.printStackTrace())
            }
            return viewContainer
        }

        private fun getInfoCopyright(): String {
            return "©2023 Stephanus Bagus Saputra"
        }
    }
}