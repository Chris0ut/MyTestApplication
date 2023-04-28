package com.example.myapplication.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.util.Constants.ARG_USERNAME
import com.example.myapplication.util.Constants.DB_NAME
import com.example.myapplication.data.local.UserHistoryDB
import com.example.myapplication.data.local.UserHistoryDao
import com.example.myapplication.data.model.UserDetails
import com.example.myapplication.data.model.UserHistory
import com.example.myapplication.databinding.FragmentDetailsBinding
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Locale

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private lateinit var userDao: UserHistoryDao
    private lateinit var db: UserHistoryDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(requireContext(), UserHistoryDB::class.java, DB_NAME).build()
        userDao = db.userDao()

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME) ?: ""
        viewModel.getUserDetails(username)
        viewModel.userDetails.observe(viewLifecycleOwner) { userDetails ->
            userDetails?.let { displayUserDetails(it) }
        }
    }

    private fun displayUserDetails(user: UserDetails) {
        val dateString = user.createdAt
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val formattedDate = outputFormat.format(inputFormat.parse(dateString))
        binding.apply {
            Glide.with(this@DetailsFragment).load(user.avatarUrl).into(userAvatar)
            userName.text = user.name
            userEmail.text = user.email ?: getString(R.string.not_available)
            userOrganization.text = user.company ?: getString(R.string.not_available)
            userFollowersCount.text = user.followersCount.toString()
            userFollowingCount.text = user.followingCount.toString()
            userType.text = user.type
            userCreatedAt.text = formattedDate
        }
        binding.progressBar.visibility = View.GONE
        binding.userDetailsLayout.visibility = View.VISIBLE
        val newUser = UserHistory(
            avatar = user.avatarUrl,
            name = user.name ?: getString(R.string.not_available),
            email = user.email ?: getString(R.string.not_available)
        )
        val existingUser = runBlocking { userDao.getUserByName(newUser.name) }
        if (existingUser == null) {
            runBlocking { userDao.insertUser(newUser) }
        }
    }

    companion object {
        fun newInstance(username: String): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle().apply {
                putString(ARG_USERNAME, username)
            }
            fragment.arguments = args
            return fragment
        }
    }
}