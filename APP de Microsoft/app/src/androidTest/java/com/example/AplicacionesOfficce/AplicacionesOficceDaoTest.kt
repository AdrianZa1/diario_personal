/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.AplicacionesOfficce

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.AplicacionesOfficce.data.AplicacionesOficceDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AplicacionesOficceDaoTest {

    private lateinit var aplicacionesOficceDao: AplicacionesOficceDao
    private lateinit var aplicacionesOficceDatabase: AplicacionesOficceDatabase
    private val aplicacionesOficce1 = AplicacionesOficce(1, "Apples", 10.0, 20, descripcion = "Apples")
    private val aplicacionesOficce2 = AplicacionesOficce(2, "Bananas", 15.0, 97, descripcion = "Apples")

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        aplicacionesOficceDatabase = Room.inMemoryDatabaseBuilder(context, AplicacionesOficceDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        aplicacionesOficceDao = aplicacionesOficceDatabase.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        aplicacionesOficceDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = aplicacionesOficceDao.getAllItems().first()
        assertEquals(allItems[0], aplicacionesOficce1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = aplicacionesOficceDao.getAllItems().first()
        assertEquals(allItems[0], aplicacionesOficce1)
        assertEquals(allItems[1], aplicacionesOficce2)
    }


    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        addOneItemToDb()
        val item = aplicacionesOficceDao.getItem(1)
        assertEquals(item.first(), aplicacionesOficce1)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        aplicacionesOficceDao.delete(aplicacionesOficce1)
        aplicacionesOficceDao.delete(aplicacionesOficce2)
        val allItems = aplicacionesOficceDao.getAllItems().first()
        assertTrue(allItems.isEmpty())
    }


    private suspend fun addOneItemToDb() {
        aplicacionesOficceDao.insert(aplicacionesOficce1)
    }

    private suspend fun addTwoItemsToDb() {
        aplicacionesOficceDao.insert(aplicacionesOficce1)
        aplicacionesOficceDao.insert(aplicacionesOficce2)
    }
}
