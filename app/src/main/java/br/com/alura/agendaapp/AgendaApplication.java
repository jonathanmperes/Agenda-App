package br.com.alura.agendaapp;

import android.app.Application;

import androidx.room.Room;

import br.com.alura.agendaapp.dao.AlunoDAO;
import br.com.alura.agendaapp.database.AgendaDatabase;
import br.com.alura.agendaapp.database.dao.RoomAlunoDAO;
import br.com.alura.agendaapp.model.Aluno;

public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosDeTeste();
    }

    private void criaAlunosDeTeste() {
        AgendaDatabase database = Room
                .databaseBuilder(this, AgendaDatabase.class, "agenda.db")
                .allowMainThreadQueries()
                .build();
        RoomAlunoDAO dao = database.getRoomAlunoDAO();
        dao.salva(new Aluno("Alex", "1122223333", "alex@alura.com.br"));
        dao.salva(new Aluno("Fran", "1122223333", "fran@alura.com.br"));
    }
}
