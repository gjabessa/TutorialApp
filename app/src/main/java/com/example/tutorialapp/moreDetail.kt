package com.example.tutorialapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import com.example.tutorialapp.data.Course
import com.example.tutorialapp.ui.login.LoginActivity
import com.example.tutorialapp.viewmodel.CourseView
import com.example.tutorialapp.viewmodel.UserView
import kotlinx.android.synthetic.main.fragment_more_detail.*
import kotlinx.android.synthetic.main.item.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.join_class.view.*
import java.io.Serializable


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [moreDetail.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [moreDetail.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class moreDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener? = null
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var desc: TextView
    lateinit var param1: Course
    lateinit var param3: String
    lateinit var category: String
    var param2 = 0
    lateinit var button: Button
    lateinit var courseView: CourseView
    lateinit var userView: UserView
    var courses = listOf<Course>()
    var icourses = listOf<Course>()
    var ecourses = listOf<Course>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        param1 = arguments?.getSerializable("course") as Course
        param2 = arguments?.getInt("pos") as Int
        param3 = arguments?.getString("level") as String
        category = arguments?.getString("category") as String

        courseView = ViewModelProviders.of(this).get(CourseView::class.java)
        userView = ViewModelProviders.of(this).get(UserView::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("taaagg",param1.description)
        imageView = imageV
        textView = title
        desc = descr

        updateView()
        button  = button4
        param1.seen = true;
        courseView.insertCourse(param1)
        //Toast.makeText(context,category, LENGTH_LONG).show()
        button.setOnClickListener {
            courseView.icourses.observe(this, Observer {
                    course -> course?.let {
                icourses = course
            }
            })
            courseView.ecourses.observe(this, Observer {
                    course -> course?.let {
                ecourses = course
            }
            })
            courseView.courses.observe(this, Observer {
                    course -> course?.let {
                        param2 += 1
                        if(param2 == course.size){
                            if(param3 =="b"){
                                param2 = 0
                                changeLevel("i")
                            } else if(param3 =="i"){
                                param2 = 0
                                changeLevel("e")

                            } else {
                                param2 = 0
                                val mDialogView = LayoutInflater.from(activity).inflate(R.layout.join_class, null)
                                val activit = activity as Context
                                val mBuilder = AlertDialog.Builder(activit)
                                    .setView(mDialogView)
                                Toast.makeText(context,"Congratulations! You have completed this tutorial", Toast.LENGTH_SHORT).show()

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
                            }

                        } else {
                            if(param3 == "i"){
                                param1 = icourses[param2] as Course
                                param1.seen = true;
                                //insertCourse(param1)
                            } else if(param3 == "e"){
                                param1 = ecourses[param2] as Course
                                param1.seen = true;
                                //insertCourse(param1)
                            } else {
                                param1 = course[param2] as Course
                                param1.seen = true;
                                //insertCourse(param1)
                            }

                            var bundle = Bundle()
                            bundle?.putSerializable("course",param1)
                            bundle.putInt("pos",param2)
                            bundle.putString("level",param3)
                            bundle.putString("category",category)
                            val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.moreDetail,true).build()
                            findNavController().navigate(R.id.moreDetail,bundle,navOptions)
                        }


            }
            })
//            Toast.makeText(context,param1.toString(), LENGTH_LONG).show()
//            var bundle = Bundle()
//            bundle?.putSerializable("course",param1)
//            bundle.putString("pos",(param2+1).toString())
//            it.findNavController().navigate(R.id.moreDetail, bundle)


        }
    }

    fun insertCourse(course: Course){
        courseView.insertCourse(course)
    }
    fun changeLevel(input: String){
        userView.loggedInUser.observe(viewLifecycleOwner, Observer {
                user -> user?.let {
            if(user.isEmpty()){
                val i = Intent(context, LoginActivity::class.java)
                context?.startActivity(i)
            } else {
                var user_ = it[0]
                user_.level = input
                userView.loginUser(user_)


                var bundle = Bundle()
                bundle.putString("level",input)
                bundle.putString("category",category)
                val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detail,true).build()
                findNavController().navigate(R.id.detail,bundle,navOptions)
            }
        }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        courseView.icourses.observe(this, Observer {
                course -> course?.let {
            icourses = course
        }
        })
        courseView.ecourses.observe(this, Observer {
                course -> course?.let {
            ecourses = course
        }
        })
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_detail, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
    fun updateView(){
        textView.text = param1.name
        desc.text = param1.description
        val circularProgressDrawable = CircularProgressDrawable(context as Context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide
            .with(context!!)
            .load(param1.large_image)
            .placeholder(circularProgressDrawable)
            .centerCrop()
            .into(imageView)
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
         * @return A new instance of fragment moreDetail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            moreDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
