package com.example.tutorialapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.recycle.retrofit.ApiClient
import com.example.recycle.retrofit.ApiClient.BASE_URL
import com.example.tutorialapp.data.Course
import com.example.tutorialapp.viewmodel.CourseView
import com.example.tutorialapp.viewmodel.UserView
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.join_class.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [detail.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [detail.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class detail : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var gridView_: GridView
    lateinit var textView: TextView
    lateinit var categ: TextView
    lateinit var adapter: CourseAdapter
    lateinit var courses: List<Course>
    lateinit var icourses: List<Course>
    lateinit var ecourses: List<Course>
    lateinit var leftbt: Button
    lateinit var rightbt: Button
    var coursesList = listOf<Course>()
    lateinit var userView: UserView
    lateinit var courseView: CourseView
    var array1 = listOf<Course>(
        Course("1","Learn how to salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://i.pinimg.com/originals/bc/a8/bc/bca8bce0f99dd1327b0ddb8b86a53b4b.gif", "https://cdn1.vectorstock.com/i/1000x1000/54/40/elegant-couple-in-love-dancing-together-in-vector-16755440.jpg","b","Lesson #1: Salsa basic step", false),

        Course("2","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps", "https://i.gifer.com/G7LN.gif", "https://cdn5.vectorstock.com/i/1000x1000/95/44/professional-dancer-couple-dancing-cha-cha-cha-vector-20069544.jpg","b","Lesson #2: Side basic dance step", false),

        Course("3","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://thumbs.gfycat.com/CautiousGloriousBlackrussianterrier-size_restricted.gif","https://cdn2.iconfinder.com/data/icons/argentina-travel-cartoon-2/512/d343_2-512.png","i", "Lesson #3",false),

        Course("4","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://thumbs.gfycat.com/ShorttermCarefreeAlbino-max-1mb.gif","https://cdn4.vectorstock.com/i/1000x1000/93/63/professional-dancer-couple-dancing-waltz-pair-of-vector-20069363.jpg","i","Lesson 4",false),

        Course("5","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://thumbs.gfycat.com/WeightyInexperiencedGlowworm-small.gif","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTFP7hjCmmWTkkfrRcjW03XTbqsEe-oRFnV8F0I6KLGDtFZf_a","e","Lesson 5",false),

        Course("6","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://media1.tenor.com/images/203c230aa755516f41c21b789a268c52/tenor.gif","https://cdn1.iconfinder.com/data/icons/argentina-cartoon/512/g10077-512.png","e","Lesson 6",false)
    )

    var array2 = listOf<Course>(
        Course("1","Learn how to salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://i.pinimg.com/originals/bc/a8/bc/bca8bce0f99dd1327b0ddb8b86a53b4b.gif", "https://cdn1.vectorstock.com/i/1000x1000/54/40/elegant-couple-in-love-dancing-together-in-vector-16755440.jpg","b","Lesson #1: Bachata basic step", false),

        Course("2","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps", "https://i.gifer.com/G7LN.gif", "https://cdn5.vectorstock.com/i/1000x1000/95/44/professional-dancer-couple-dancing-cha-cha-cha-vector-20069544.jpg","b","Lesson #2: Side basic dance step", false),

        Course("3","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://thumbs.gfycat.com/CautiousGloriousBlackrussianterrier-size_restricted.gif","https://cdn2.iconfinder.com/data/icons/argentina-travel-cartoon-2/512/d343_2-512.png","i", "Bachata Lesson #3",false),

        Course("4","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://thumbs.gfycat.com/ShorttermCarefreeAlbino-max-1mb.gif","https://cdn4.vectorstock.com/i/1000x1000/93/63/professional-dancer-couple-dancing-waltz-pair-of-vector-20069363.jpg","i","Lesson 4",false),

        Course("5","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://thumbs.gfycat.com/WeightyInexperiencedGlowworm-small.gif","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTFP7hjCmmWTkkfrRcjW03XTbqsEe-oRFnV8F0I6KLGDtFZf_a","e","Lesson 5",false),

        Course("6","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://media1.tenor.com/images/203c230aa755516f41c21b789a268c52/tenor.gif","https://cdn1.iconfinder.com/data/icons/argentina-cartoon/512/g10077-512.png","e","Lesson 6",false)
    )

    var array3 = listOf<Course>(
        Course("1","Learn how to salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://i.pinimg.com/originals/bc/a8/bc/bca8bce0f99dd1327b0ddb8b86a53b4b.gif", "https://cdn1.vectorstock.com/i/1000x1000/54/40/elegant-couple-in-love-dancing-together-in-vector-16755440.jpg","b","Lesson #1: Kizomba basic step", false),

        Course("2","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps", "https://i.gifer.com/G7LN.gif", "https://cdn5.vectorstock.com/i/1000x1000/95/44/professional-dancer-couple-dancing-cha-cha-cha-vector-20069544.jpg","b","Lesson #2: Side basic dance step", false),

        Course("3","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://thumbs.gfycat.com/CautiousGloriousBlackrussianterrier-size_restricted.gif","https://cdn2.iconfinder.com/data/icons/argentina-travel-cartoon-2/512/d343_2-512.png","i", "Kizomba Lesson #3",false),

        Course("4","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://thumbs.gfycat.com/ShorttermCarefreeAlbino-max-1mb.gif","https://cdn4.vectorstock.com/i/1000x1000/93/63/professional-dancer-couple-dancing-waltz-pair-of-vector-20069363.jpg","i","Lesson 4",false),

        Course("5","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://thumbs.gfycat.com/WeightyInexperiencedGlowworm-small.gif","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTFP7hjCmmWTkkfrRcjW03XTbqsEe-oRFnV8F0I6KLGDtFZf_a","e","Lesson 5",false),

        Course("6","Learn another popular salsa dance with the main basic step of Salsa. This is the step you will go in and out of all the moves. The move consists of 2 steps","https://media1.tenor.com/images/203c230aa755516f41c21b789a268c52/tenor.gif","https://cdn1.iconfinder.com/data/icons/argentina-cartoon/512/g10077-512.png","e","Lesson 6",false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("level")
            param2 = it.getString("category")
        }
        userView = ViewModelProviders.of(this).get(UserView::class.java)
        adapter = CourseAdapter((activity as Context), coursesList)
        courseView = ViewModelProviders.of(this).get(CourseView::class.java)
        courseView.courses.observe(this, Observer {
                course -> course?.let {
            if(it.isNullOrEmpty()){
                var array = array1
                var bundle = Bundle()
                bundle.putString("level", "b")

                if(param2 == "Bachata"){
                    array = array2
                } else if(param2 == "Kizomba"){
                    array = array3
                }
                array?.forEach{
                    updateView(it)
                    Log.d("taga",it.id)
                }
            }
        }
        })
        var call: Call<List<Course>> = ApiClient.getClient.getCourses()
        if(param1 == "i"){
            call = ApiClient.getClient.getIntermediateCourses()
        } else if(param1 == "e"){
            call = ApiClient.getClient.getExpertCourses()
        }
        call.enqueue(object : Callback<List<Course>> {

            override fun onResponse(call: Call<List<Course>>?, response: Response<List<Course>>?) {
                //progerssProgressDialog.dismiss()
                val list: List<Course>? = response?.body();
                list?.forEach{
                    updateView(it)
                }
            }

            override fun onFailure(call: Call<List<Course>>?, t: Throwable?) {
                //progerssProgressDialog.dismiss()
                Log.d("tag","er")
                Log.d("tag",t.toString())
            }
        })
    }

    override fun onStart() {
        //Toast.makeText(context,"start", LENGTH_LONG).show()
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }
    fun updateView(value: Course){
        courseView.insertCourse(value)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        textView = level
        categ = category

        var text = textView.text
        textView.text = " "

        leftbt = left
        rightbt = right
        if(param1 =="i"){
            text = "Intermediate Courses"
            leftbt.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("level", "b")
                val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detail,true).build()
                findNavController().navigate(R.id.detail, bundle,navOptions)
            }
            rightbt.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("level", "e")
                val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detail,true).build()
                findNavController().navigate(R.id.detail, bundle,navOptions)
            }

        } else if(param1 == "e"){
            text = "Expert Courses"
            leftbt.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("level", "i")
                val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detail,true).build()
                findNavController().navigate(R.id.detail, bundle,navOptions)
            }
            rightbt.isEnabled = false
            val mDialogView = LayoutInflater.from(activity).inflate(R.layout.join_class, null)
            val activit = activity as Context
            val mBuilder = AlertDialog.Builder(activit)
                .setView(mDialogView)

            //show dialog
            val  mAlertDialog = mBuilder.show()
            mDialogView.joinclass.setOnClickListener {
                val url = it.getTag() as String

                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)

                //pass the url to intent data
                intent.data = Uri.parse(url)

                startActivity(intent)
            }

        } else {
            leftbt.isEnabled = false
            rightbt.setOnClickListener {
                var bundle = Bundle()
                bundle.putString("level", "i")
                val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detail,true).build()
                findNavController().navigate(R.id.detail, bundle,navOptions)
            }
        }
        userView.getCategory.observe(this, Observer {
            if(it.isNotEmpty()){
                param2 = it[0]

                if(param2 == "Salsa"){
                    categ.text = "Salsa"
                }
                else if(param2 == "Bachata"){
                    categ.text = "Bachata"
                } else if(param2 == "Kizomba"){
                    categ.text = "Kizomba"
                }
                textView.text = text
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Toast.makeText(context,"cv", LENGTH_LONG).show()
        /*Toast.makeText(context,param1, LENGTH_LONG).show()*/
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        gridView_ = view.gridview
        gridView_.adapter = adapter

        doo()
        // Inflate the layout for this fragment
        return view

    }

    fun doo(){
        courseView = ViewModelProviders.of(this).get(CourseView::class.java)
        userView.getLevel.observe(this, Observer { level ->
            level?.let {
                if(param1.isNullOrEmpty()) {
                    if (!it.isEmpty()) {
                       // Toast.makeText(context,"cv2", LENGTH_LONG).show()
                        param1 = it[0]
                        if (param1 == "i") {
                            var bundle = Bundle()
                            bundle.putString("level", "i")
                            val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detail,true).build()
                            findNavController().navigate(R.id.detail, bundle,navOptions)
                        } else if (param1 == "e") {
                            var bundle = Bundle()
                            bundle.putString("level", "e")

                            val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detail,true).build()
                            findNavController().navigate(R.id.detail, bundle,navOptions)
                        }
                    }
                }
            }
        })
    //        Toast.makeText(context,"cv1", LENGTH_LONG).show()
        if(param1 == "b"){
            //Toast.makeText(context,"cv3", LENGTH_LONG).show()
            courseView.courses.observe(this, Observer {
                    course -> course?.let {
                //Toast.makeText(context,"cv3", LENGTH_LONG).show()
                adapter.setCourses(course)
            }
            })
        } else if(param1 =="i"){
            courseView.icourses.observe(this, Observer {
                    course -> course?.let { adapter.setCourses(course) }
            })
        } else if(param1 == "e"){
            courseView.ecourses.observe(this, Observer {
                    course -> course?.let { adapter.setCourses(course) }
            })
        } else {
            courseView.courses.observe(this, Observer {
                    course -> course?.let {
               // Toast.makeText(context,"cv4", LENGTH_LONG).show()
                adapter.setCourses(course) }
            })
        }

        userView.getCategory.observe(this, Observer {
            if(it.isNotEmpty()){
                param2 = it[0]
                adapter.category(it[0])
            }

        })
    }

    class CourseAdapter : BaseAdapter {
        var courseList: List<Course> = emptyList()
        var context: Context? = null
        var category: String?  = null

        constructor(context: Context, coursesList:List<Course>) : super() {
            this.context = context
        }


        override fun getCount(): Int {
            return courseList.size
        }

        override fun getItem(position: Int): Any {
            return courseList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        fun setCourses(course: List<Course>){

            this.courseList = course
            notifyDataSetChanged()

        }
        fun category(category: String?){
            this.category = category
            notifyDataSetChanged()
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val course = this.courseList[position]

            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var courseView = inflator.inflate(R.layout.item, null)
            val circularProgressDrawable = CircularProgressDrawable(context as Context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide
                .with(context!!)
                .load(course.small_image)
                .placeholder(circularProgressDrawable)
                .centerCrop()
                .into(courseView.menu_image)
            courseView.menu_name.text = course.name!!
            var bundle = Bundle()
            courseView.setOnClickListener {
                bundle.putSerializable("course",course)
                bundle.putInt("pos",position)
                bundle.putString("level",course.level)
                bundle.putString("category",category)
                it.findNavController().navigate(R.id.moreDetail, bundle)
            }
            if(course.seen){
                var frame_: FrameLayout
                frame_ = courseView.item_frame
                frame_.setBackgroundColor(Color.MAGENTA)

            }
            return courseView
        }
    }

    override fun onAttach(context: Context) {
        //Toast.makeText(context,"attach", LENGTH_LONG).show()
        super.onAttach(context)


    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            detail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
