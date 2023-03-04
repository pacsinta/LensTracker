import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class Home extends StatefulWidget {
  const Home({ Key? key }) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {

  Future<void> _updateDate(DateTime time){
    if(FirebaseAuth.instance.currentUser == null){
      throw Exception("Must be logged in!");
    }

    return FirebaseFirestore.instance
      .collection("LensDates")
      .doc(FirebaseAuth.instance.currentUser!.uid)
      .set({
        'date': time
      });
  }

  Future<String> _getDate() async {
    if(FirebaseAuth.instance.currentUser == null){
      throw Exception("Must be logged in!");
    }

    try{
      dynamic data = await FirebaseFirestore.instance
      .collection("LensDates")
      .doc(FirebaseAuth.instance.currentUser!.uid)
      .get();

      return _timeToString(data['date']);
    }catch(e){
      return _timeToString(null);
    }
  }

  String _timeToString(Timestamp? timestamp){
    if(timestamp == null){
      return "no date found";
    }

    DateTime time = timestamp.toDate();
    return "${time.year.toString()}.${time.month.toString()}.${time.day.toString()}";
  }

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              FutureBuilder<String>(
                future: _getDate(),
                builder: (BuildContext context, AsyncSnapshot<String> snapshot){
                  if(snapshot.hasData){
                    return Column(
                      children: [
                        Text(snapshot.data!, style: const TextStyle(fontSize: 52),),
                        TextButton(
                          onPressed:() async {
                            await _updateDate(DateTime.now());
                            setState(() {});
                          }, 
                          child: const Text("Update date")
                          )
                      ],
                    ); 
                  }else if(snapshot.hasError){
                    return Text(snapshot.error.toString());
                  }
                  else{
                    return const CircularProgressIndicator();
                  }
                },
              ),
              TextButton(
                onPressed: (){
                  FirebaseAuth.instance.signOut();
                  context.go('/');
                }, 
                child: const Text("Sign Out")
                )
            ]
            ),
        ),
    );
  }
}