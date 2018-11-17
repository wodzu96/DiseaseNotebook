package com.ikurek.diseasesnotebook.ui.diseasedetailsactivity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ikurek.diseasesnotebook.R
import com.ikurek.diseasesnotebook.base.BaseApp
import com.ikurek.diseasesnotebook.communication.api.model.DiseasePostAPI
import com.ikurek.diseasesnotebook.communication.model.DiseaseModel
import kotlinx.android.synthetic.main.activity_disease_details.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DiseaseDetailsActivity : AppCompatActivity(), DiseaseDetailsContract.View {
    companion object {
        private const val DISEASE_ID = "DISEASE_ID"

        fun getStartingIntent(context: Context, id: Int?) =
            Intent(context, DiseaseDetailsActivity::class.java).apply {
                id?.let {
                    putExtra(DISEASE_ID, it)
                }
            }
    }

    @Inject
    lateinit var presenter: DiseaseDetailsContract.Presenter

    private val startCalendar = Calendar.getInstance()
    private val finishCalendar = Calendar.getInstance()
    val myFormat = "dd-MM-yyyy" //In which you need put here
    val sdf = SimpleDateFormat(myFormat, Locale.US)
    var diseaseId: Int? = null

    private var dateForStart =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            startCalendar.set(Calendar.YEAR, year)
            startCalendar.set(Calendar.MONTH, monthOfYear)
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            startDateEt.setText(sdf.format(startCalendar.time))
        }

    private var dateForFinish =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            finishCalendar.set(Calendar.YEAR, year)
            finishCalendar.set(Calendar.MONTH, monthOfYear)
            finishCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            finishDateEt.setText(sdf.format(finishCalendar.time))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.activityComponent.inject(this)
        presenter.attach(this)
        setContentView(R.layout.activity_disease_details)
        setDateListeners()
        intent.getIntExtra(DISEASE_ID, -1).let {
            if (it != -1) {
                presenter.getDisease(it)
                diseaseId = it
                saveOrEditBt.text = getString(R.string.edit_disease)
                deleteBt.setOnClickListener {
                    presenter.deleteDisease(diseaseId!!)
                }
            }
            else{
                saveOrEditBt.text = getString(R.string.save_disease)
                deleteBt.visibility = View.GONE
            }
        }
        saveOrEditBt.setOnClickListener { onSaveOrEditClicked() }
    }

    override fun showDiseaseDetails(disease: DiseaseModel) {
        diseaseTitleEt.setText(disease.diseaseName)
        diseaseConditionEt.setText(disease.diseaseCondition)
        drugsEt.setText(disease.drugs)
        startDateEt.setText(disease.startDate)
        finishDateEt.setText(disease.finishDate)
    }

    private fun setDateListeners() {
        startDateEt.setOnClickListener {
            DatePickerDialog(
                this, dateForStart, startCalendar
                    .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        finishDateEt.setOnClickListener {
            DatePickerDialog(
                this, dateForFinish, finishCalendar
                    .get(Calendar.YEAR), finishCalendar.get(Calendar.MONTH),
                finishCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    fun onSaveOrEditClicked() {
        if (diseaseId == null) {
            presenter.postDisease(
                DiseasePostAPI(
                    diseaseTitleEt.text.toString(),
                    diseaseConditionEt.text.toString(),
                    drugsEt.text.toString(),
                    startDateEt.text.toString(),
                    finishDateEt.text.toString()
                )
            )
        } else {
            presenter.patchDisease(
                DiseasePostAPI(
                    diseaseTitleEt.text.toString(),
                    diseaseConditionEt.text.toString(),
                    drugsEt.text.toString(),
                    startDateEt.text.toString(),
                    finishDateEt.text.toString()
                ),
                diseaseId!!
            )
        }
    }

    override fun close() {
        presenter.detach()
        finish()
    }
}
