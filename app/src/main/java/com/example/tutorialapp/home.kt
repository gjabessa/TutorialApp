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
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.recycle.retrofit.ApiClient
import com.example.tutorialapp.data.Course
import com.example.tutorialapp.data.User
import com.example.tutorialapp.ui.login.LoginActivity
import com.example.tutorialapp.viewmodel.CourseView
import com.example.tutorialapp.viewmodel.UserView
import kotlinx.android.synthetic.main.fragment_home.*
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
 * [home.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [home.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var category: String? = null
    lateinit var param2: User
    private var listener: OnFragmentInteractionListener? = null
    lateinit var button1: Button
    lateinit var button_2: Button
    lateinit var button_3: Button
    lateinit var userView: UserView
    lateinit var courseView: CourseView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userView = ViewModelProviders.of(this).get(UserView::class.java)
        courseView = ViewModelProviders.of(this).get(CourseView::class.java)

//        Log.d("level",level)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button1 = button
        button_2 = button2
        button_3 = button3
        userView.loggedInUser.observe(this, Observer {
                user -> user?.let {
            if(user.isEmpty()){
                val i = Intent(context,LoginActivity::class.java)
                context?.startActivity(i)
            }
            else {
                param2 = user[0]
                category = param2.category
            }

        button1.setOnClickListener{
            val bundle = Bundle();
            bundle.putString("level","b")
            bundle.putString("category",category)
            param2.level = "b"
            userView.insertUser(param2)
            courseView.deleteCourses()
            it.findNavController().navigate(R.id.detail,bundle)

        }
        button2.setOnClickListener{
            val bundle = Bundle();
            param2.level = "i"
            userView.insertUser(param2)
            bundle.putString("level","i")
            bundle.putString("category",category)
            Toast.makeText(context,category, LENGTH_LONG).show()
            courseView.deleteCourses()
            it.findNavController().navigate(R.id.detail,bundle)

        }
        button3.setOnClickListener{
            val bundle = Bundle();
            param2.level = "e"
            userView.insertUser(param2)
            bundle.putString("level","e")
            bundle.putString("category",category)
            courseView.deleteCourses()
            it.findNavController().navigate(R.id.detail,bundle)

        }

        }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val binding: com.example.tutorialapp.databinding.FragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        Toast.makeText(context,"hiii", LENGTH_LONG).show()
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
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
