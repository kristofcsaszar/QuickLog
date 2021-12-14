package hu.bme.aut.quicklog.logs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.quicklog.dataModel.LogDataBase
import hu.bme.aut.quicklog.dataModel.LogItem
import hu.bme.aut.quicklog.databinding.LogMainFragmentBinding
import java.util.*
import kotlin.concurrent.thread

class LogMainFragment : Fragment(), LogAdapter.LogItemClickListener, AddLogFragment.AddLogListener {
    private lateinit var binding: LogMainFragmentBinding
    private lateinit var applicationContext: Context
    private lateinit var database: LogDataBase
    private lateinit var adapter: LogAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //super.onCreateView(inflater, container, savedInstanceState)
        binding = LogMainFragmentBinding.inflate(inflater, container, false)
        Log.d("startup", "MainFragment onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = LogDataBase.getDatabase(applicationContext)

        Log.d("startup", "MainFragment onViewCreated")
        initRecyclerView()
        binding.fab.setOnClickListener {
            // using childFragmentManager, so the DialogFragment can reach this one
            AddLogFragment().show(this.childFragmentManager, AddLogFragment::class.java.simpleName)
        }
    }


    private fun initRecyclerView() {
        adapter = LogAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(activity)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.logItemDao().getAll()
            activity?.runOnUiThread{
                Log.d("persistence","updating items in adapter")
                adapter.update(items)
            }
        }
    }


    override fun onItemChanged(item: LogItem) {
        thread {
            database.logItemDao().update(item)
        }
    }

    override fun onLogAdded(
        title: String,
        score: Int,
        description: String,
        year: Int,
        month: Int,
        day: Int
    ) {
        Log.d("persistence","onLogAdded $title $description")
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val logDataItem = LogItem(null,title, score,description, calendar.time)
        adapter.addItem(logDataItem)
        thread {
            database.logItemDao().insert(logDataItem)
        }
    }
}