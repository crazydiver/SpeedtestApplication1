package ru.scoltech.openran.speedtest.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputEditText
import ru.scoltech.openran.speedtest.R


class SetupPipelineTab : Fragment() {
    companion object {
        private val TAG = SetupPipelineTab::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setup_pipeline, container, true)
    }

    private fun getPipleineCount() = run {
        requireActivity().getSharedPreferences("pipeline_count",
            AppCompatActivity.MODE_PRIVATE).getString("0", "0")
            .toString().toInt()
    }

    private fun serializePipeline(pipeline : View): String {
        val name = pipeline.findViewById<TextInputEditText>(R.id.pipeline_name).text.toString()
        val device = pipeline.findViewById<TextInputEditText>(R.id.device_args).text.toString()
        val server = pipeline.findViewById<TextInputEditText>(R.id.server_args).text.toString()
        println("$name\n$device\n$server".split('\n'))
        return "$name\n$device\n$server"
    }

    private fun editNewCallback(name : Int, parent : ViewGroup, pipelineForm : View){
        val pipelineCount = getPipleineCount()
        parent.getChildAt(pipelineCount).findViewById<EditText>(name)
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


                    requireActivity().getSharedPreferences(
                        "iperf_args_pipeline",
                        AppCompatActivity.MODE_PRIVATE
                    ).edit {
                        putString("${pipelineCount + 1}", serializePipeline(
                            pipelineForm))
                    }
                }
            })
    }

    private fun editBasicCallback(view : View, activity : FragmentActivity){
        val iperfUploadServText = view.findViewById<EditText>(R.id.upload_server_args)
        iperfUploadServText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val editor = activity.getSharedPreferences(
                    getString(R.string.iperfSharedPreferences),
                    AppCompatActivity.MODE_PRIVATE
                ).edit()
                editor.putString(getString(R.string.upload_server_args), s.toString())
                editor.apply()
                Log.d(TAG, "update UploadServerArgs = $s")
            }
        })
    }

    private fun addChild2List(){
        val childCountEditor = requireActivity().getSharedPreferences("pipeline_count",
            AppCompatActivity.MODE_PRIVATE)

        var newList =
            "${childCountEditor.getString("1", "")} " +
                    "${childCountEditor.getString("0", "0").toString().toInt()}"

        if (newList[0] == ' ')
            newList = newList.drop(1)
        childCountEditor.edit {putString("1", newList)}.apply{}
        childCountEditor.edit {
            putString("0", (getPipleineCount() + 1).toString())
        }

    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        val iperfPref = activity.getSharedPreferences(
            getString(R.string.iperfSharedPreferences), AppCompatActivity.MODE_PRIVATE
        )
        val DOWNLOAD_DEVICE_IPERF_ARGS = iperfPref.getString(
            getString(R.string.download_device_args),
            getString(R.string.default_download_device_iperf_args)
        )
        val DOWNLOAD_SERVER_IPERF_ARGS = iperfPref.getString(
            getString(R.string.download_server_args),
            getString(R.string.default_download_server_iperf_args)
        )
        val UPLOAD_DEVICE_IPERF_ARGS = iperfPref.getString(
            getString(R.string.upload_device_args),
            getString(R.string.default_upload_device_iperf_args)
        )
        val UPLOAD_SERVER_IPERF_ARGS = iperfPref.getString(
            getString(R.string.upload_server_args),
            getString(R.string.default_upload_server_iperf_args)
        )

        val iperfUploadDevText = view.findViewById<EditText>(R.id.upload_device_args)
        iperfUploadDevText.setText(UPLOAD_DEVICE_IPERF_ARGS)
//        editCallback(R.id.upload_device_args, view, )
        view.findViewById<EditText>(R.id.upload_device_args)
            .addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    requireActivity().getSharedPreferences(
                        getString(R.string.iperfSharedPreferences),
                        AppCompatActivity.MODE_PRIVATE
                    ).edit{ putString(getString(R.string.upload_device_args), s.toString()) }
                    Log.d(TAG, "update UploadDeviceArgs = $s")
                }
            })

        val iperfUploadServText = view.findViewById<EditText>(R.id.upload_server_args)
        iperfUploadServText.setText(UPLOAD_SERVER_IPERF_ARGS)
        iperfUploadServText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val editor = activity.getSharedPreferences(
                    getString(R.string.iperfSharedPreferences),
                    AppCompatActivity.MODE_PRIVATE
                ).edit()
                editor.putString(getString(R.string.upload_server_args), s.toString())
                editor.apply()
                Log.d(TAG, "update UploadServerArgs = $s")
            }
        })

        val iperfDownloadDevText = view.findViewById<EditText>(R.id.download_device_args)
        iperfDownloadDevText.setText(DOWNLOAD_DEVICE_IPERF_ARGS)
        iperfDownloadDevText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val editor = activity.getSharedPreferences(
                    getString(R.string.iperfSharedPreferences),
                    AppCompatActivity.MODE_PRIVATE
                ).edit()
                editor.putString(getString(R.string.download_device_args), s.toString())
                editor.apply()
                Log.d(TAG, "update DownloadDeviceArgs = $s")
            }
        })

        val iperfDownloadServText = view.findViewById<EditText>(R.id.download_server_args)
        iperfDownloadServText.setText(DOWNLOAD_SERVER_IPERF_ARGS)
        iperfDownloadServText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val editor = activity.getSharedPreferences(
                    getString(R.string.iperfSharedPreferences),
                    AppCompatActivity.MODE_PRIVATE
                ).edit()
                editor.putString(getString(R.string.download_server_args), s.toString())
                editor.apply()
                Log.d(TAG, "update DownloadServerArgs = $s")
            }
        })

    val iperfName1DevText = view.findViewById<EditText>(R.id.n1_device_args)
        iperfName1DevText.setText(DOWNLOAD_SERVER_IPERF_ARGS)
        iperfName1DevText.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val editor = activity.getSharedPreferences(
                        getString(R.string.iperfSharedPreferences),
                        AppCompatActivity.MODE_PRIVATE
                    ).edit()
                    editor.putString(getString(R.string.download_server_args), s.toString())
                    editor.apply()
                    Log.d(TAG, "update DownloadServerArgs = $s")
                }
            })
    val iperfName1ServText = view.findViewById<EditText>(R.id.n1_server_args)
        iperfName1ServText.setText(DOWNLOAD_SERVER_IPERF_ARGS)
        iperfName1ServText.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val editor = activity.getSharedPreferences(
                        getString(R.string.iperfSharedPreferences),
                        AppCompatActivity.MODE_PRIVATE
                    ).edit()
                    editor.putString(getString(R.string.download_server_args), s.toString())
                    editor.apply()
                    Log.d(TAG, "update DownloadServerArgs = $s")
                }
            })

    val iperfName2DevText = view.findViewById<EditText>(R.id.n2_device_args)
        iperfName2DevText.setText(DOWNLOAD_SERVER_IPERF_ARGS)
        iperfName2DevText.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val editor = activity.getSharedPreferences(
                        getString(R.string.iperfSharedPreferences),
                        AppCompatActivity.MODE_PRIVATE
                    ).edit()
                    editor.putString(getString(R.string.download_server_args), s.toString())
                    editor.apply()
                    Log.d(TAG, "update DownloadServerArgs = $s")
                }
            })

        val iperfName2ServText = view.findViewById<EditText>(R.id.n2_server_args)
        iperfName2ServText.setText(DOWNLOAD_SERVER_IPERF_ARGS)
        iperfName2ServText.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val editor = activity.getSharedPreferences(
                        getString(R.string.iperfSharedPreferences),
                        AppCompatActivity.MODE_PRIVATE
                    ).edit()
                    editor.putString(getString(R.string.download_server_args), s.toString())
                    editor.apply()
                    Log.d(TAG, "update DownloadServerArgs = $s")
                }
            })

    val iperfName3DevText = view.findViewById<EditText>(R.id.n3_device_args)
        iperfName3DevText.setText(DOWNLOAD_SERVER_IPERF_ARGS)
        iperfName3DevText.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val editor = activity.getSharedPreferences(
                        getString(R.string.iperfSharedPreferences),
                        AppCompatActivity.MODE_PRIVATE
                    ).edit()
                    editor.putString(getString(R.string.download_server_args), s.toString())
                    editor.apply()
                    Log.d(TAG, "update DownloadServerArgs = $s")
                }
            })

        val iperfName3ServText = view.findViewById<EditText>(R.id.n3_server_args)
        iperfName3ServText.setText(DOWNLOAD_SERVER_IPERF_ARGS)
        iperfName3ServText.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val editor = activity.getSharedPreferences(
                        getString(R.string.iperfSharedPreferences),
                        AppCompatActivity.MODE_PRIVATE
                    ).edit()
                    editor.putString(getString(R.string.download_server_args), s.toString())
                    editor.apply()
                    Log.d(TAG, "update DownloadServerArgs = $s")
                }
            })

        /*

        val childCountPreferences = requireActivity().getSharedPreferences("pipeline_count",
            AppCompatActivity.MODE_PRIVATE)
        val childCount = childCountPreferences.getString("0", "0")
            .toString().toInt()

        val addPipelineButton = view.findViewById<Button>(R.id.add_pipeline_button)
        val pipelineLayout = view.findViewById<LinearLayout>(R.id.new_pipelines)

        val pipelineEditor = requireActivity().getSharedPreferences(
            "iperf_args_pipeline",
            AppCompatActivity.MODE_PRIVATE)
        println(" [][]" + childCount)
        var pipelineList = mutableListOf<Int>()
        if (childCountPreferences.getString("1", "").toString() != "") {
            pipelineList = childCountPreferences.getString("1", "").toString()
                .split(' ').map { it.toInt() }.toMutableList()

            for (index in pipelineList) {

                LayoutInflater.from(requireContext())
                    .inflate(R.layout.pipeline_sample, pipelineLayout)

                val pipelineConfig = pipelineEditor.getString("$index", "\n\n").toString()
                    .split('\n')
                println(pipelineEditor)

                print("index = $index")
                pipelineLayout.getChildAt(index).findViewById<EditText>(R.id.pipeline_name)
                    .setText(pipelineConfig[0])
                pipelineLayout.getChildAt(index).findViewById<EditText>(R.id.device_args)
                    .setText(pipelineConfig[1])
                pipelineLayout.getChildAt(index).findViewById<EditText>(R.id.server_args)
                    .setText(pipelineConfig[2])


                editNewCallback(R.id.pipeline_name, pipelineLayout, pipelineLayout)
                editNewCallback(R.id.device_args, pipelineLayout, pipelineLayout)
                editNewCallback(R.id.server_args, pipelineLayout, pipelineLayout)


                pipelineLayout.getChildAt(index).findViewById<ImageButton>(R.id.deleteButton)
                    .setOnClickListener {
                        pipelineEditor.edit().remove("$index").apply()
                        println("remove $index")
                        pipelineList.remove(index)
                        pipelineLayout.removeViewAt(index)
                    }
            }
        }

        addPipelineButton.setOnClickListener { newPipeline(pipelineLayout) }
        */
    }

    private fun newPipeline(parent: ViewGroup) {
        val pipelineForm = LayoutInflater.from(requireContext())
            .inflate(R.layout.pipeline_sample, parent)

        addChild2List()

        editNewCallback(R.id.pipeline_name, parent, pipelineForm)
        editNewCallback( R.id.device_args, parent, pipelineForm)
        editNewCallback(R.id.server_args, parent, pipelineForm)

        requireActivity().getSharedPreferences(
            "iperf_args_pipeline",
            AppCompatActivity.MODE_PRIVATE
        ).edit {
            putString("${getPipleineCount() + 1}", serializePipeline(
                pipelineForm))
        }
    }

}


