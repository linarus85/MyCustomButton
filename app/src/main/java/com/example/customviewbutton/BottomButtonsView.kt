package com.example.customviewbutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customviewbutton.databinding.BottunBinding

enum class BittomAction {
    POSITIVE, NEGATIVE
}
typealias OnButtonListenerAction = (BittomAction) -> Unit

class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: BottunBinding
    private var listener: OnButtonListenerAction? = null
    var isProgress: Boolean = false
        set(value) {
            field = value
            with(binding) {
                if (value) {
                    progressBar.visibility = VISIBLE
                    positiveButton.visibility = INVISIBLE
                    negativeButton.visibility = INVISIBLE
                } else {
                    progressBar.visibility = INVISIBLE
                    positiveButton.visibility = VISIBLE
                    negativeButton.visibility = VISIBLE
                }
            }
        }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, R.style.MyButtonStyle)

    constructor(context: Context, attrs: AttributeSet?)
            : this(context, attrs, R.attr.bottomDefaultAttr)

    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.bottun, this, true)
        binding = BottunBinding.bind(this)
        initAttr(attrs, defStyleAttr, defStyleRes)
        initListener()
    }

    private fun initAttr(
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        if (attrs == null) return
        val typeArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BottomButtonsView,
            defStyleAttr,
            defStyleRes
        )
        val positiveButtonText =
            typeArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtunText)
        setPositiveText(positiveButtonText)
        val negativeButtonText =
            typeArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtunText)
        setNegativeText(negativeButtonText)
        val positiveButtonColor = typeArray.getColor(
            R.styleable.BottomButtonsView_bottomPositiveButtunColor,
            Color.GREEN
        )
        binding.positiveButton.backgroundTintList = ColorStateList.valueOf(positiveButtonColor)
        val negativeButtonColor = typeArray.getColor(
            R.styleable.BottomButtonsView_bottomNegativeButtunColor,
            Color.YELLOW
        )
        binding.negativeButton.backgroundTintList = ColorStateList.valueOf(negativeButtonColor)
        isProgress = typeArray.getBoolean(
            R.styleable.BottomButtonsView_bottomProgress,
            false
        )
        typeArray.recycle()
    }

    fun initListener() {
        binding.positiveButton.setOnClickListener {
            this.listener?.invoke(BittomAction.POSITIVE)
        }
        binding.negativeButton.setOnClickListener {
            this.listener?.invoke(BittomAction.NEGATIVE)
        }
    }

    fun setListener(listener: OnButtonListenerAction?) {
        this.listener = listener
    }

    fun setPositiveText(text: String?) {
        binding.positiveButton.text = text
    }

    fun setNegativeText(text: String?) {
        binding.negativeButton.text = text ?: "Cancel"
    }

    override fun onSaveInstanceState(): Parcelable? {
        val state = super.onSaveInstanceState()!!
        val saveState = SaveState(state)
       saveState.positiveButtonText = binding.positiveButton.text.toString()
  return saveState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val saveState = state as SaveState
        super.onRestoreInstanceState(saveState.superState)
        binding.positiveButton.text = saveState.positiveButtonText

    }

    class SaveState : BaseSavedState {
        var positiveButtonText: String? = null

        constructor(superState: Parcelable) : super(superState)
        constructor(parcel: Parcel) : super(parcel) {
            positiveButtonText = parcel.readString()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(positiveButtonText)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SaveState> = object :
                Parcelable.Creator<SaveState> {
                override fun createFromParcel(source: Parcel): SaveState {
                    return SaveState(source)
                }

                override fun newArray(size: Int): Array<SaveState?> {
                    return Array(size) { null }
                }

            }

        }
    }
}