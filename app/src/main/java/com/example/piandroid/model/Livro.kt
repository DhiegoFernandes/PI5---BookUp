package com.example.piandroid.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Parcelize
@Entity
class Livro (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String?,
    val paginas: Int?,
    val paginasLidas: Int?

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nome)
        parcel.writeString(paginas.toString())
        parcel.writeString(paginasLidas.toString())

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Livro> {
        override fun createFromParcel(parcel: Parcel): Livro {
            return Livro(parcel)
        }

        override fun newArray(size: Int): Array<Livro?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Livro(id=$id, nome='$nome', paginas=$paginas, paginasLidas=$paginasLidas)"
    }
    
}
annotation class Parcelize