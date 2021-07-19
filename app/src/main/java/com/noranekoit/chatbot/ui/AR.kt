package com.noranekoit.chatbot.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.ar.core.Anchor
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.SkeletonNode
import com.google.ar.sceneform.animation.ModelAnimator
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.noranekoit.chatbot.R
import com.noranekoit.chatbot.data.Message
import com.noranekoit.chatbot.util.BotResponse
import com.noranekoit.chatbot.util.Constants
import com.noranekoit.chatbot.util.Time
import kotlinx.android.synthetic.main.activity_ar.*
import kotlinx.android.synthetic.main.activity_chatbot.*

import kotlinx.coroutines.*
import java.util.*

class AR : AppCompatActivity() {
    lateinit var arFragment: ArFragment
    private lateinit var model: Uri
    private var renderable : ModelRenderable? = null




    private var check: Boolean = false

    private var check2: Int? = null
    //voice
    private val RQ_SPEECH_REC =102


    //
    private val botList = listOf("Ronald", "Bun","Chatrin")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)


//            //INVISIBLE
//            animate_kick_button.setVisibility(View.INVISIBLE)
//            animate_idle_button.setVisibility(View.INVISIBLE)
//            animate_boxing_button.setVisibility(View.INVISIBLE)
//            tv_voice.setVisibility(View.INVISIBLE)
//            btn_voice_ar.setVisibility(View.INVISIBLE)


        tv_voice.setEnabled(false)
        tv_voice_me.setEnabled(false)
        btn_voice_ar.setEnabled(false)





        arFragment =sceneform_fragment as ArFragment
        model = Uri.parse("chracter.sfb")

        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->

            if (plane.type!= Plane.Type.HORIZONTAL_UPWARD_FACING){
                return@setOnTapArPlaneListener
            }
            var anchor = hitResult.createAnchor()
            peaceObject(arFragment, anchor,model)
        }
//
//        animate_kick_button.setOnClickListener { animateModel("Character|Kick") }
//        animate_idle_button.setOnClickListener { animateModel("Character|Idle") }
//        animate_boxing_button.setOnClickListener { animateModel("Character|Boxing") }
//

        //voice
        btn_voice_ar.setOnClickListener {
            askSpeechInput()


        }

        //
        val random = (0..3).random()
        customMessage("Hello!kali ini kamu berbicara dengan ${botList[random]}, ada yang bisa dibantu?")


    }

    //

    private fun customMessage(message: String) {
        tv_voice.text = message


    }






//    private fun animateModel(name: String){
//        animator?.let { it->
//            if (it.isRunning){
//                it.end()
//            }
//        }
//
//        renderable?.let { modelRenderable ->
//            val data =modelRenderable.getAnimationData(name)
//            animator = ModelAnimator(data,modelRenderable)
//            animator?.start()
//        }
//    }


    private fun peaceObject(arFragment: ArFragment, anchor: Anchor?, model: Uri?) {

        ModelRenderable.builder()
            .setSource(arFragment.context,model)
            .build()
            .thenAccept {
                renderable = it
                addtoScene(arFragment,anchor,it)
            }
            .exceptionally {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(it.message).setTitle("error")
                val dialog = builder.create()
                dialog.show()
                return@exceptionally null
            }
    }

    private fun addtoScene(arFragment: ArFragment, anchor: Anchor?, it: ModelRenderable?) {
        val anchorNode = AnchorNode(anchor)
        val skeletonNode = SkeletonNode()
        skeletonNode.renderable = renderable
        val node = TransformableNode(arFragment.transformationSystem)
        node.addChild(skeletonNode)
        node.setParent(anchorNode)

        if (check==false){
            arFragment.arSceneView.scene.addChild(anchorNode)
            tv_voice.setEnabled(true)
            tv_voice_me.setEnabled(true)
            btn_voice_ar.setEnabled(true)

            check=true
        }


    }


    //voice
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            tv_voice_me.text =result?.get(0).toString()
            val message  = tv_voice_me.text.toString()

            botResponse(message)
        }
    }

    private fun botResponse(message: String){
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {

            delay(200)

            withContext(Dispatchers.Main) {

                val response = BotResponse.basicResponse(message)

                Message(response, Constants.RECEIVE_ID, timeStamp)

                tv_voice.text = response

                when (response) {
                    Constants.OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    Constants.OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                    Constants.INFO_BEASISWA -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.ukdc.ac.id/?page_id=3959")
                        startActivity(site)
                    }
                    Constants.BIAYA_KULIAH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.ukdc.ac.id/penmaru/?page_id=28")
                        startActivity(site)
                    }



                }
            }
        }
    }

    //voice
    private fun askSpeechInput(){
        if (!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this,"Speech Recognition is not Available", Toast.LENGTH_SHORT).show()

        }else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!")
            startActivityForResult(i,RQ_SPEECH_REC)
        }

    }


}