package com.pet001kambala.view

import com.pet001kambala.numberpad.KeyEvent
import com.pet001kambala.numberpad.KeyEventObserver
import com.pet001kambala.numberpad.KeyPad
import javafx.application.Platform
import javafx.beans.property.StringProperty
import javafx.scene.input.KeyCode
import javafx.stage.StageStyle
import tornadofx.*

class KeyboardController : View("Keyboard") {

    val tModel: TextModel by inject()

    override val root = stackpane {
        add(KeyPad().apply {

            setOnKeyPressed(object : KeyEventObserver {
                override fun onKeyEvent(evt: KeyEvent) {
                    var curTxt = tModel.property.get() ?: ""
                    val keyTxt = evt.source.textProperty().get()
                    val kMeta = evt.source.metaDataProperty()?.get()

                    curTxt = when {
                        kMeta == KeyCode.SEPARATOR || keyTxt.isNumber() -> curTxt + keyTxt
                        kMeta == KeyCode.DELETE -> curTxt.substring(0, if (curTxt.isEmpty()) 0 else curTxt.length - 1)
                        kMeta == KeyCode.CLEAR -> ""
                        kMeta == KeyCode.ENTER -> {
                            Platform.runLater {
                                close()
                            }
                            curTxt
                        }
                        else -> curTxt
                    }
                    tModel.property.set(curTxt)
                }
            })
        })
    }

    override fun onBeforeShow() {
        super.onBeforeShow()
        currentStage?.isResizable = false
        currentStage?.initStyle(StageStyle.UTILITY)
    }


    companion object{
        fun Any?.isNumber(): Boolean {
            return this != null &&
                    try {
                        this.toString().toDouble()
                        true
                    } catch (e: Exception) {
                        false
                    }
        }
    }
}

class TextModel(val property: StringProperty): ItemViewModel<String>()