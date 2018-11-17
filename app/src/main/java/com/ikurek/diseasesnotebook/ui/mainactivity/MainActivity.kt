package com.ikurek.diseasesnotebook.ui.mainactivity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikurek.diseasesnotebook.R
import com.ikurek.diseasesnotebook.base.BaseApp
import com.ikurek.diseasesnotebook.communication.model.DiseaseModel
import com.ikurek.diseasesnotebook.ui.diseasedetailsactivity.DiseaseDetailsActivity
import com.ikurek.diseasesnotebook.utlis.Session
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val viewModels = mutableListOf<DiseaseModel>()

    lateinit var diseaseAdapter : DiseaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setTitle(R.string.my_drugs)
        BaseApp.activityComponent.inject(this)
        presenter.attach(this)
        diseaseAdapter = DiseaseAdapter(applicationContext, viewModels)
        diseaseList.adapter = diseaseAdapter
        fab.setOnClickListener {
            startActivity(DiseaseDetailsActivity.getStartingIntent(this, null))
        }
        signOutIv.setOnClickListener { signOut() }
        refreshIv.setOnClickListener {  presenter.refreshData() }

    }

    override fun showDiseasesList(diseases: List<DiseaseModel>) {
        viewModels.clear()
        viewModels.addAll(diseases)
        diseaseAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun signOut() {
        this.finish()
        Session.signOut(this)
    }
}
