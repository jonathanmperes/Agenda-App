package br.com.alura.agendaapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.com.alura.agendaapp.database.dao.RoomAlunoDAO;
import br.com.alura.agendaapp.model.Aluno;

@Database(entities = {Aluno.class}, version = 1, exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {
    public abstract RoomAlunoDAO getRoomAlunoDAO();
}
