package gg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Player {

    static LinkedList<Music> listaReproducao = new LinkedList<>();
    static boolean usingPlayList = false; // Variável que marca quando uma Thread está usando o recurso
    static Lock mutex = new ReentrantLock();
    static Condition playlistUseCondition = mutex.newCondition();
    static Music currentMusic; // Representa a musica que está tocando
    static int currentMusicIndex = 0; // Indice da música que está tocando
    static DefaultListModel<String> playlistModel = new DefaultListModel<>();


    public static void main(String[] args) { }

    static class Music {
        private final String nameMusic;
        private final int durationSong; // Em segundos

        public Music(String nameMusic, int durationMusic) {
            this.nameMusic = nameMusic;
            this.durationSong = durationMusic;
        }

        public String getNameMusic() {
            return nameMusic;
        }

        public int getDurationSong() {
            return durationSong;
        }

    }

    static class AddMusic extends Thread{
        private final Music music;

        public AddMusic(Music music) { this.music = music; }

        @Override
        public void run(){
            mutex.lock();
            try {
                while(usingPlayList){
                    playlistUseCondition.await(); //Espere enquanto o recurso compartilhado estiver sendo usado.
                }
                usingPlayList = true; // Thread irá utilzar o recurso
                if(listaReproducao.isEmpty()) currentMusic = music;
                listaReproducao.add(music);
                usingPlayList = false; // Thread terminou a operação
                playlistUseCondition.signalAll(); //Sinaliza para as outras Threads que podem usar o recurso

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                mutex.unlock();
            }
        }
    }

     // Removeremos as músicas da list baseado em seu nome
    //  Só removeremos uma música caso exista na lista
    static class RemoveMusic extends Thread{
        private final String nameMusic;

        public RemoveMusic(String nameMusic) { this.nameMusic = nameMusic; }

        @Override
        public void run() {
            mutex.lock();
            try {
                while(usingPlayList){
                    playlistUseCondition.await(); // Espere enquanto o recurso compartilhado estiver sendo usado.
                }
                usingPlayList = true;

                int indexToRemove = checkIfContainMusic(nameMusic);
                if (indexToRemove != -1) listaReproducao.remove(indexToRemove);

                usingPlayList = false;
                playlistUseCondition.signalAll(); // Sinaliza para as outras Threads que podem usar o recurso

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                mutex.unlock();
            }
        }

         private int checkIfContainMusic(String nameMusic) {
             int indexToRemove = -1;
             for (Player.Music m : listaReproducao) {
                 if (m.getNameMusic().equalsIgnoreCase(nameMusic)) {
                     indexToRemove = listaReproducao.indexOf(m);
                 }
             }
             return indexToRemove;
         }
    }

    static class NextORprevMusic extends Thread{
        private final boolean nextCalled; // Botão next pressionado
        private final boolean previousCalled; // Botão previous pressionado

        public NextORprevMusic(boolean nextCalled, boolean previousCalled) {
            this.nextCalled = nextCalled;
            this.previousCalled = previousCalled;
        }

        @Override
        public void run(){

            mutex.lock();
            try {
                while(usingPlayList){
                    playlistUseCondition.await();
                }
                usingPlayList = true;

                if (nextCalled) {
                    if (!(listaReproducao.isEmpty()) && (currentMusicIndex < listaReproducao.size()-1)) {
                        ++currentMusicIndex;
                        currentMusic = listaReproducao.get(currentMusicIndex);
                    }

                }else if (previousCalled){
                    if (!(listaReproducao.isEmpty()) && (currentMusicIndex > 0)) {
                        --currentMusicIndex;
                        currentMusic = listaReproducao.get(currentMusicIndex);
                    }
                }

                usingPlayList = false;
                playlistUseCondition.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                mutex.unlock();
            }

        }
    }

    }








