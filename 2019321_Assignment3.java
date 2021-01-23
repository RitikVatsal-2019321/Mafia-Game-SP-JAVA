//RITIK VATSAL ||  2019321
//Advanced Programming Assignment 3

package com.company;
import javafx.scene.effect.Bloom;
import javafx.scene.web.WebHistory;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.SQLOutput;
import java.util.*;
import java.lang.*;


public class Main implements com.company.Helper {
    private static Object obj=new Object();
    public static void main(String args[]) throws InterruptedException {


        System.out.println(start);
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Random rand=new Random();
        while (n < 6) {
            System.out.println("Minimum 6 Players required!!  ReEnter...");
            n = in.nextInt();
        }

        System.out.println(choice);
        int role = in.nextInt();
        com.company.GenericClass<Integer> GOver=new com.company.GenericClass();
        GOver.setWin(0);

        com.company.GenericClass<Integer> condition=new com.company.GenericClass();
        condition.setWin(0);
        int mafN = n / 5;
        int detN = n / 5;
        int healN = Math.max(n / 10, 1);
        int comN = n - mafN - detN - healN;

        while (!(role==1 || role==2 ||role==3 ||role==4 ||role ==5)){
            System.out.println(invalid);
            System.out.println(choice);
            role=in.nextInt();
        }

        com.company.Printer pr1nt=new com.company.Printer();
        String[] pln=new String[n];
        int[] status=new int[n];
        for (int i = 0; i <n ; i++) {
            pln[i]="Player"+(i+1);
            status[i]=1;
        }

        int[] lobby=PopulateLobby(n,role);


        role=lobby[0];


        System.out.println(selfRR+pln[0]+" | "+DefineClass(lobby[0]));


        if (lobby[0]!=4){
            System.out.println("Other "+DefineClass(lobby[0])+"s Are - ");
            for (int i = 1; i < n; i++) {
                if (lobby[i]==lobby[0]){
                    System.out.println(pln[i]);
                }
            }}


        ArrayList<com.company.Player> plist=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            plist.add(new com.company.Player(lobby[i]));
//            System.out.println(lobby[i]);
        }
        int roundcntr=0;


        while (GOver.getWin().equals(condition.getWin())) {
            roundcntr++;
            System.out.println("--- Round "+roundcntr+" ---");
            pr1nt.printAlive(status,pln);

            if (role == 1) {
                if (status[0]==1){System.out.println("Choose a Target...");
                    int trgt=in.nextInt()-1;
                    plist=HowTheMafiaWorks(plist,trgt,status);}

                else if (mafN>0){
                    plist=HowTheMafiaWorks(plist,MafiaSIM(lobby,status),status);
                }

//                  for (int i = 0; i <n ; i++) {
//                    System.out.println(i);
//                    System.out.println(plist.get(i).getCateg());
//                    System.out.println(plist.get(i).getHealth());
//                    System.out.println();
//                }

                System.out.println("Mafia have chosen their target.");
                int vote=0;

                if (detN>0){int detTEST=DetSIM(lobby,status);


                    if (lobby[detTEST]==1){
                        vote=-1;
                        System.out.println(pln[detTEST] +" is a Mafia!");
                        status[detTEST]=-1;
                        mafN--;
                    }}

                System.out.println("Detectives have chosen a player to test.");

                int Healed= MedSIM(n,status);
                if (healN>0){plist.get(Healed).setHealth();}
                System.out.println("Healers have chosen someone to heal.");

                for (int i = 0; i <n ; i++) {
                    if (status[i]==1 && lobby[i]!=1){
                        if (plist.get(i).getHealth()<=0){
                            status[i]=-1;
                            System.out.println(pln[i]+ " was killed!!");
                            if (lobby[i]==2){
                                detN--;
                            }
                            if (lobby[i]==3){
                                healN--;
                            }
                        }

                    }

                }

                if (vote==0){
                    int resultOFvote=-1;

                    if (status[0]==1){
                        System.out.println("Select a person to vote out: ");
                        resultOFvote=VOTE(n,in.nextInt(),status);
                        while (resultOFvote==-1){
                            System.out.println("Draw In votes. Revote... ");
                            resultOFvote=VOTE(n,in.nextInt()-1,status);
                        }
                    }
                    else{
                        resultOFvote=VOTE(n,status);

                        while (resultOFvote==-1){
                            System.out.println("Draw In votes. Revoting... ");
                            resultOFvote=VOTE(n,status);

                        }
                    }
                    plist.get(resultOFvote).setHealth(-plist.get(resultOFvote).getHealth());
                    status[resultOFvote]=-1;

                    System.out.println(pln[resultOFvote]+" was voted out!");
                    if (lobby[resultOFvote]==1){
                        mafN--;
                    }
                    if (lobby[resultOFvote]==2){
                        detN--;
                    }
                    if (lobby[resultOFvote]==3){
                        healN--;
                    }

                }

                if (mafN==0){
                    GOver.setWin(1);
                    System.out.println("The Mafia has Lost");
                    break;
                }
                int remain=0;
                for (int i = 0; i < n; i++) {
                    if (status[i]==1){
                        remain++;
                    }
                }
                remain=remain-mafN;
                if (remain<=mafN){
                    GOver.setWin(1);
                    System.out.println("The Mafia has WON");
                    break;
                }




            }

            else if (role == 2) {
                plist=HowTheMafiaWorks(plist,MafiaSIM(lobby,status),status);
                System.out.println("Mafia have chosen their target.");
                int detTEST;
                int vote=0;
                if (status[0]==1){
                    System.out.println("Choose Player for testing... ");
                    detTEST=in.nextInt()-1;
                    while (lobby[detTEST]==2 || status[detTEST]==-1){
                        System.out.println("Chosen Player is a Detective or Not Active. Please Choose again... ");
                        detTEST=in.nextInt()-1;
                    }
                    if (lobby[detTEST]==1){
                        vote=-1;
                        System.out.println(pln[detTEST] +" is a Mafia!");
                        status[detTEST]=-1;
                        mafN--;
                    }

                    else{
                        System.out.println(pln[detTEST]+" Was not a Mafia :(");
                    }

                }

                else if (detN>0){
                    detTEST=DetSIM(lobby,status);


                    if (lobby[detTEST]==1){
                        vote=-1;
                        System.out.println(pln[detTEST] +" is a Mafia!");
                        status[detTEST]=-1;
                        mafN--;
                    }

                    else{
                        System.out.println(pln[detTEST]+" Was not a Mafia :(");
                    }
                }

                System.out.println("Detectives have chosen a player to test.");


                int Healed= MedSIM(n,status);
                if (healN>0){plist.get(Healed).setHealth();}
                System.out.println("Healers have chosen someone to heal.");

                for (int i = 0; i <n ; i++) {
                    if (status[i]==1 && lobby[i]!=1){
                        if (plist.get(i).getHealth()<=0){
                            status[i]=-1;
                            System.out.println(pln[i]+ " was killed!!");
                            if (lobby[i]==2){
                                detN--;
                            }
                            if (lobby[i]==3){
                                healN--;
                            }
                        }

                    }

                }

                if (vote==0){
                    int resultOFvote=-1;

                    if (status[0]==1){
                        System.out.println("Select a person to vote out: ");
                        resultOFvote=VOTE(n,in.nextInt(),status);
                        while (resultOFvote==-1){
                            System.out.println("Draw In votes. Revote... ");
                            resultOFvote=VOTE(n,in.nextInt()-1,status);
                        }
                    }
                    else{
                        resultOFvote=VOTE(n,status);

                        while (resultOFvote==-1){
                            System.out.println("Draw In votes. Revoting... ");
                            resultOFvote=VOTE(n,status);

                        }
                    }
                    plist.get(resultOFvote).setHealth(-plist.get(resultOFvote).getHealth());
                    status[resultOFvote]=-1;

                    System.out.println(pln[resultOFvote]+" was voted out!");
                    if (lobby[resultOFvote]==1){
                        mafN--;
                    }
                    if (lobby[resultOFvote]==2){
                        detN--;
                    }
                    if (lobby[resultOFvote]==3){
                        healN--;
                    }

                }

                if (mafN==0){
                    GOver.setWin(1);
                    System.out.println("The Mafia has Lost");
                    break;
                }
                int remain=0;
                for (int i = 0; i < n; i++) {
                    if (status[i]==1){
                        remain++;
                    }
                }
                remain=remain-mafN;
                if (remain<=mafN){
                    GOver.setWin(1);
                    System.out.println("The Mafia has WON");
                    break;
                }
            }

            else if (role == 3) {

                plist=HowTheMafiaWorks(plist,MafiaSIM(lobby,status),status);
                System.out.println("Mafia have chosen their target.");


                int detTEST=DetSIM(lobby,status);
                int vote=0;
                System.out.println("Detectives have chosen a player to test.");
                if (detN>0){if (lobby[detTEST]==1){
                    vote=-1;
                    System.out.println(pln[detTEST] +" is a Mafia!");
                    status[detTEST]=-1;
                    mafN--;
                }}
                int Healed;

                if (status[0]==1){
                    System.out.println("Choose someone to Heal... ");
                    Healed=in.nextInt();
                    plist.get(Healed).setHealth();
                }
                else if (healN>0){ Healed= MedSIM(n,status);
                    plist.get(Healed).setHealth();
                }


                System.out.println("Healers have chosen someone to heal.");

                for (int i = 0; i <n ; i++) {
                    if (status[i]==1 && lobby[i]!=1){
                        if (plist.get(i).getHealth()<=0){
                            status[i]=-1;
                            System.out.println(pln[i]+ " was killed!!");
                            if (lobby[i]==2){
                                detN--;
                            }
                            if (lobby[i]==3){
                                healN--;
                            }
                        }
                    }

                }

                if (vote==0){
                    int resultOFvote=-1;

                    if (status[0]==1){
                        System.out.println("Select a person to vote out: ");
                        resultOFvote=VOTE(n,in.nextInt(),status);
                        while (resultOFvote==-1){
                            System.out.println("Draw In votes. Revote... ");
                            resultOFvote=VOTE(n,in.nextInt()-1,status);
                        }
                    }
                    else{
                        resultOFvote=VOTE(n,status);

                        while (resultOFvote==-1){
                            System.out.println("Draw In votes. Revoting... ");
                            resultOFvote=VOTE(n,status);

                        }
                    }
                    plist.get(resultOFvote).setHealth(-plist.get(resultOFvote).getHealth());
                    status[resultOFvote]=-1;

                    System.out.println(pln[resultOFvote]+" was voted out!");
                    if (lobby[resultOFvote]==1){
                        mafN--;
                    }
                    if (lobby[resultOFvote]==2){
                        detN--;
                    }
                    if (lobby[resultOFvote]==3){
                        healN--;
                    }

                }

                if (mafN==0){
                    GOver.setWin(1);
                    System.out.println("The Mafia has Lost");
                    break;
                }
                int remain=0;
                for (int i = 0; i < n; i++) {
                    if (status[i]==1){
                        remain++;
                    }
                }
                remain=remain-mafN;
                if (remain<=mafN){
                    GOver.setWin(1);
                    System.out.println("The Mafia has WON");
                    break;
                }
            }

            else if (role == 4) {

                plist=HowTheMafiaWorks(plist,MafiaSIM(lobby,status),status);
                System.out.println("Mafia have chosen their target.");


                int detTEST=DetSIM(lobby,status);
                int vote=0;
                System.out.println("Detectives have chosen a player to test.");
                if (detN>0){if (lobby[detTEST]==1){
                    vote=-1;
                    System.out.println(pln[detTEST] +" is a Mafia!");
                    status[detTEST]=-1;
                    mafN--;
                }}


                int Healed= MedSIM(n,status);
                if (healN>0){plist.get(Healed).setHealth();}
                System.out.println("Healers have chosen someone to heal.");




                for (int i = 0; i <n ; i++) {
                    if (status[i]==1 && lobby[i]!=1){
                        if (plist.get(i).getHealth()<=0){
                            status[i]=-1;
                            System.out.println(pln[i]+ " was killed!!");
                            if (lobby[i]==2){
                                detN--;
                            }
                            if (lobby[i]==3){
                                healN--;
                            }
                        }
                    }

                }

                if (vote==0){
                    int resultOFvote=-1;

                    if (status[0]==1){
                        System.out.println("Select a person to vote out: ");
                        resultOFvote=VOTE(n,in.nextInt(),status);
                        while (resultOFvote==-1){
                            System.out.println("Draw In votes. Revote... ");
                            resultOFvote=VOTE(n,in.nextInt()-1,status);
                        }
                    }
                    else{
                        resultOFvote=VOTE(n,status);

                        while (resultOFvote==-1){
                            System.out.println("Draw In votes. Revoting... ");
                            resultOFvote=VOTE(n,status);

                        }
                    }
                    plist.get(resultOFvote).setHealth(-plist.get(resultOFvote).getHealth());
                    status[resultOFvote]=-1;

                    System.out.println(pln[resultOFvote]+" was voted out!");
                    if (lobby[resultOFvote]==1){
                        mafN--;
                    }
                    if (lobby[resultOFvote]==2){
                        detN--;
                    }
                    if (lobby[resultOFvote]==3){
                        healN--;
                    }

                }

                if (mafN==0){
                    GOver.setWin(1);
                    System.out.println("The Mafia has Lost");
                    break;
                }
                int remain=0;
                for (int i = 0; i < n; i++) {
                    if (status[i]==1){
                        remain++;
                    }
                }
                remain=remain-mafN;
                if (remain<=mafN){
                    GOver.setWin(1);
                    System.out.println("The Mafia has WON");
                    break;
                }
            }
            long sec=1000;
//            obj.wait();
//            obj.notify();
        }

        pr1nt.PlayerPrinter(lobby,pln);
        System.out.println();
    }

//    public static void PlayerPrinter(int[] lobby, String[]pln){
//        int nn =lobby.length;
//        ArrayList<Integer>[] roles =new ArrayList[4];
//        for (int i = 0; i <4 ; i++) {
//            roles[i]=new ArrayList<Integer>();
//        }
//        for (int i = 0; i < nn ; i++) {
//            roles[lobby[i]-1].add(i);
//        }
//
//        System.out.println("\nThe Mafia were - ");
//        for (int i = 0; i <roles[0].size() ; i++) {
//            System.out.println(pln[roles[0].get(i)]);
//        }
//        System.out.println("\nThe Detectives were - ");
//        for (int i = 0; i <roles[1].size() ; i++) {
//            System.out.println(pln[roles[1].get(i)]);
//        }
//        System.out.println("\nThe Healers were - ");
//        for (int i = 0; i <roles[2].size() ; i++) {
//            System.out.println(pln[roles[2].get(i)]);
//        }
//        System.out.println("\nThe Commoners were - ");
//        for (int i = 0; i <roles[3].size() ; i++) {
//            System.out.println(pln[roles[3].get(i)]);
//        }
//    }

    public static int MafiaSIM(int[] lobby, int[] status){
        Random rand=new Random();

        int cho=rand.nextInt(lobby.length);
        while (lobby[cho]==1 || status[cho]==-1){
            cho=rand.nextInt(lobby.length);
        }
        return cho;
    }

    public static int DetSIM(int[] lobby, int[] status){
        Random rand=new Random();

        int cho=rand.nextInt(lobby.length);
        while (lobby[cho]==2 || status[cho]==-1){
            cho=rand.nextInt(lobby.length);
        }
        return cho;
    }

    public static int MedSIM(int n, int[] status){
        Random rand=new Random();

        int cho=rand.nextInt(n);
        while (status[cho]==-1){
            cho=rand.nextInt(n);
        }
        return cho;
    }

    public static ArrayList HowTheMafiaWorks(ArrayList<com.company.Player> plist, int trgt, int[] status ){                               //IMPLEMENTS EQUALS()
        int damage=0;
        int numMaf=0;
        for (int i = 0; i <plist.size() ; i++) {
            if (plist.get(i).getCateg()==1 && status[i]==1){
                if (plist.get(i).getHealth()>0){
                    damage=damage+plist.get(i).getHealth();
                    numMaf++;
                }
            }
        }
        int trgtHp=plist.get(trgt).getHealth();
        if (damage>=plist.get(trgt).getHealth()){
            plist.get(trgt).setHealth(-plist.get(trgt).getHealth());
        }
        else{
            plist.get(trgt).setHealth(-damage);
        }

        plist=DmgDstr(plist,trgtHp,status);
        return plist;
    }

    public static ArrayList DmgDstr(ArrayList<com.company.Player> plist, int dmg, int[] status){
        ArrayList<com.company.MafiaMem> temp=new ArrayList<>();

        for (int i = 0; i < plist.size() ; i++) {
            if (plist.get(i).getCateg()==1 && status[i]==1){
                if (plist.get(i).getHealth()>0){
                    temp.add(new com.company.MafiaMem(i, plist.get(i).getHealth()));
                }
            }
        }

        Collections.sort(temp);
        if (temp.size()==0){
            return plist;
        }
        int cc=0;
        int size=temp.size();
        int ndmg=dmg/size;

        while (temp.get(cc).getHealth()<ndmg){
            dmg=dmg-temp.get(cc).getHealth();
            temp.get(cc).setHealth(-temp.get(cc).getHealth());
            size=size-1;
            if (size==0){
                break;
            }
            ndmg=dmg/size;
            cc++;
        }

        for (int i = 0; i < size; i++) {
            temp.get(cc+i).setHealth(-ndmg);
        }


        for (int i = 0; i <temp.size() ; i++) {
            plist.get(temp.get(i).getPid()).setHealth(temp.get(i).getHealth()-plist.get(temp.get(i).getPid()).getHealth());
        }


        return plist;
    }

    public static int[] PopulateLobby(int n, int role){
        int[] lobby=new int[n];
        int mafN = n / 5;
        int detN = n / 5;
        int healN = Math.max(n / 10, 1);
        int comN = n - mafN - detN - healN;
        int rndm;
        Random rand=new Random();
        rndm=rand.nextInt(n);

        if (role == 1) {
            mafN--;
            lobby[0]=1;
            for (int i = 0; i <mafN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=1;
            }
            for (int i = 0; i <detN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=2;
            }
            for (int i = 0; i <healN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=3;
            }
            for (int i = 0; i <n ; i++) {
                if (lobby[i]==0){
                    lobby[i]=4;
                }
            }

        }

        else if (role == 2) {
            detN--;
            lobby[0]=2;
            for (int i = 0; i <mafN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=1;
            }
            for (int i = 0; i <detN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=2;
            }
            for (int i = 0; i <healN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=3;
            }
            for (int i = 0; i <n ; i++) {
                if (lobby[i]==0){
                    lobby[i]=4;
                }
            }
        }

        else if (role == 3) {
            healN--;
            lobby[0]=3;
            for (int i = 0; i <mafN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=1;
            }
            for (int i = 0; i <detN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=2;
            }
            for (int i = 0; i <healN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=3;
            }
            for (int i = 0; i <n ; i++) {
                if (lobby[i]==0){
                    lobby[i]=4;
                }
            }
        }

        else if (role == 4) {
            comN--;
            lobby[0]=4;
            for (int i = 0; i <mafN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=1;
            }
            for (int i = 0; i <detN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=2;
            }
            for (int i = 0; i <healN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=3;
            }
            for (int i = 0; i <n ; i++) {
                if (lobby[i]==0){
                    lobby[i]=4;
                }
            }
        }

        else{

            for (int i = 0; i <mafN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=1;
            }
            for (int i = 0; i <detN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=2;
            }
            for (int i = 0; i <healN ; i++) {
                while(lobby[rndm]!=0){
                    rndm=rand.nextInt(n);
                }
                lobby[rndm]=3;
            }
            for (int i = 0; i <n ; i++) {
                if (lobby[i]==0){
                    lobby[i]=4;
                }
            }

        }
        return lobby;
    }

    public static String DefineClass(int categ){
        if (categ==1){
            return "Mafia";
        }
        if (categ==2){
            return "Detective";
        }
        if (categ==3){
            return "Healer";
        }
        if (categ==4){
            return "Commoner";
        }
        return "Error - Class Error!";
    }

    public static int VOTE(int n, int vote, int[] status){
        int[]votes=new int[n];
        votes[0]=vote;
        Random rand =new Random();
        for (int i = 1; i < n; i++) {
            if (status[i]==1){votes[i]=rand.nextInt(n);
                while (status[votes[i]]==-1){
                    votes[i]=rand.nextInt(n);
                }}
        }

        int[] vcnt=new int[n];
        for (int i = 0; i < n; i++) {
            vcnt[votes[i]]=vcnt[votes[i]]+1;
        }
        int max=0;
        int who=-1;
        int bruh=0;

        for (int i = 0; i < n; i++) {
            if (status[i]==-1){
                vcnt[i]=0;
            }
        }
        for (int i = 0; i <n ; i++) {
            if (vcnt[i]>max){
                max=vcnt[i];
                who=i;
                bruh=0;
            }
            else if (vcnt[i]==max){
                max=vcnt[i];
                who=i;
                bruh=3;
            }
        }

        if (bruh==3){
            return -1;
        }
        else{
            return who;
        }

    }

    public static int VOTE(int n,  int[] status){
        int[]votes=new int[n];

        Random rand =new Random();
        for (int i = 0; i < n; i++) {
            if (status[i]==1){
                votes[i]=rand.nextInt(n);
                while (status[votes[i]]==-1){
                    votes[i]=rand.nextInt(n);
                }
            }
//            else{
//                votes[i]=-1;
//            }
        }

        int[] vcnt=new int[n];
        for (int i = 0; i < n; i++) {
            vcnt[votes[i]]=vcnt[votes[i]]+1;
        }
        for (int i = 0; i < n; i++) {
            if (status[i]==-1){
                vcnt[i]=0;
            }
        }
        int max=0;
        int who=-1;
        int bruh=0;
        for (int i = 0; i <n ; i++) {
            if (vcnt[i]>max){
                max=vcnt[i];
                who=i;
                bruh=0;
            }
            else if (vcnt[i]==max){
                max=vcnt[i];
                who=i;
                bruh=3;
            }
        }

        if (bruh==3){
            return -1;
        }
        else{
            return who;
        }

    }

//    @Override
//    public  void wait() throws InterruptedException{}
}

interface Helper{
    final String start="\n----------- Welcome to MAFIA -----------\n Enter the number of Players (MINIMUM 6) >>";
    final String choice="|| Choose your Role (Enter Number) ||\n1. Mafia\n2. Detective\n3. Healer\n4. Commoner\n5. Random";
    final String invalid="xxxx Enter Valid Number xxxx";
    final String selfRR= "You are ";
}

abstract class PlayerFunctions{
    public abstract int getHealth();
    public abstract void setHealth(int change);
    public abstract int getCateg();

}

class Player extends com.company.PlayerFunctions {
    private int health;
    private int categ;
    private int doa;    // Dead(-1) or Alive(1)

    public Player(int categ){
        if (categ==1){this.health=2500;}
        if (categ==2){this.health=800;}
        if (categ==3){this.health=800;}
        if (categ==4){this.health=1000;}

        this.categ=categ;
        this.doa=1;
    }

    public int getDoa() {
        return doa;
    }

    public void setDoa(int doa) {
        this.doa = doa;
    }

    public  int getHealth(){
        return this.health;
    }
    public  void setHealth(int change){
        this.health=this.health+change;
    }

    public void setHealth(){
        this.health=this.health+500;
    }

    @Override
    public int getCateg() {
        return categ;
    }
}

class MafiaMem implements Comparable<com.company.MafiaMem>{
    private int pid;
    private int health;




    public MafiaMem(int pid, int health){
        this.pid=pid;
        this.health=health;
    }

    public int getPid() {
        return pid;
    }

    public void setHealth(int health) {
        this.health = this.health+health;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public int compareTo(com.company.MafiaMem mafiaMem) {
        return health-mafiaMem.getHealth();
    }
}

class GenericClass<T>{
    private T win;

    public void setWin(T win) {
        this.win = win;
    }

    public T getWin() {
        return win;
    }
}

class Printer{
    public void printAlive(int[] status, String[] pln){
        int count=0;
        System.out.println("Players Alive are - ");
        for (int i = 0; i <status.length ; i++) {
            if (status[i]==1){
                System.out.print(pln[i] + " | ");
                count++;
            }
        }
        System.out.println("Total Alive - "+count);
    }
    public void PlayerPrinter(int[] lobby, String[]pln){
        int nn =lobby.length;
        ArrayList<Integer>[] roles =new ArrayList[4];
        for (int i = 0; i <4 ; i++) {
            roles[i]=new ArrayList<Integer>();
        }
        for (int i = 0; i < nn ; i++) {
            roles[lobby[i]-1].add(i);
        }

        System.out.println("\nThe Mafia were - ");
        for (int i = 0; i <roles[0].size() ; i++) {
            System.out.println(pln[roles[0].get(i)]);
        }
        System.out.println("\nThe Detectives were - ");
        for (int i = 0; i <roles[1].size() ; i++) {
            System.out.println(pln[roles[1].get(i)]);
        }
        System.out.println("\nThe Healers were - ");
        for (int i = 0; i <roles[2].size() ; i++) {
            System.out.println(pln[roles[2].get(i)]);
        }
        System.out.println("\nThe Commoners were - ");
        for (int i = 0; i <roles[3].size() ; i++) {
            System.out.println(pln[roles[3].get(i)]);
        }
    }
}
