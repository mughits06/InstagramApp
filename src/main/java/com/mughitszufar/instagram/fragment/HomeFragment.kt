package com.mughitszufar.instagram.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mughitszufar.instagram.R
import com.mughitszufar.instagram.adapter.PostAdapter
import com.mughitszufar.instagram.model.Post
import kotlinx.android.synthetic.main.activity_login.*

class HomeFragment : Fragment() {

    private var postAdapter: PostAdapter? = null
    private var postList: MutableList<Post>? = null
    private var followingList: MutableList<Post>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        var recyclerView: RecyclerView? = null
        recyclerView = view.findViewById(R.id.home_recyclerview)
        val LinearLayoutManager = LinearLayoutManager(context)
        LinearLayoutManager.reverseLayout = true
        LinearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = LinearLayoutManager

        postList = ArrayList()
        postAdapter = context?.let { PostAdapter(it, postList as ArrayList<Post>) }
        recyclerView.adapter = postAdapter

        cekFollowing()

        return view
    }

    private fun cekFollowing() {
        followingList = ArrayList()

        val followingRef = FirebaseDatabase.getInstance().reference
            .child("Follow").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("Following")

        followingRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    (followingList as ArrayList<String>).clear()

                    for (snapshot in p0.children){
                        snapshot.key?.let { (followingList as ArrayList<String>).add(it) }
                    }

                    getPost()


                }

                
            }

            private fun getPost() {
                val postsRef = FirebaseDatabase.getInstance().reference.child("Posts")

                postsRef.addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        postList?.clear()

                        for (snaphot in p0.children){
                            val post = snaphot.getValue(Post::class.java)
                            for (id in (followingList as ArrayList<String>)){
                                if (post!!.getPublisher() == id){
                                    postList!!.add(post)

                                }

                                postAdapter!!.notifyDataSetChanged()
                            }
                        }
                    }
                })
            }
        })
    }
}
