package com.pet001kambala.numberpad

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import kotlin.system.exitProcess

object Demo : Application() {

    private lateinit var numberPad: KeyPad

    override fun init() {
        numberPad = KeyPad(5.0, 5.0)
        val pressedObserver = object : KeyEventObserver {
            override fun onKeyEvent(evt: KeyEvent) {
                println(
                    evt.source.getMetaData().toString() + " pressed"
                )
            }
        }
        val releasedObserver = object : KeyEventObserver {
            override fun onKeyEvent(evt: KeyEvent) {
                println(
                    evt.source.getMetaData().toString() + " released"
                )
            }
        }
        numberPad.setOnKeyPressed(pressedObserver)
        numberPad.setOnKeyReleased(releasedObserver)
    }

    override fun start(stage: Stage) {
        val pane = StackPane(numberPad)
        val scene = Scene(pane)
        stage.title = "NumberPad"
        stage.scene = scene
        stage.show()
    }

    override fun stop() {
        exitProcess(0)
    }


    @JvmStatic
    fun main(args: Array<String>) {
        launch(*args)
    }
}