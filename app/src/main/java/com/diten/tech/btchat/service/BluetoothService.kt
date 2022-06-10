package com.diten.tech.btchat.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class BluetoothService {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private var isBluetoothScanPermissionGranted = false
    private var isBluetoothConnectPermissionGranted = false
    private var isAccessCoarseLocationPermissionGranted = false
    private var isAccessFineLocationPermissionGranted = false

    private var isBluetoothPermissionGranted = false
    private var isBluetoothAdminPermissionGranted = false
    private var isBluetoothAdvertisePermissionGranted = false

    fun PermissionsAllow(context: Context,activity:AppCompatActivity){
        permissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            isBluetoothScanPermissionGranted = permissions[Manifest.permission.BLUETOOTH_SCAN] ?: isBluetoothScanPermissionGranted
            isBluetoothConnectPermissionGranted = permissions[Manifest.permission.BLUETOOTH_CONNECT] ?: isBluetoothConnectPermissionGranted

            isBluetoothPermissionGranted = permissions[Manifest.permission.BLUETOOTH] ?: isBluetoothPermissionGranted
            isBluetoothAdminPermissionGranted = permissions[Manifest.permission.BLUETOOTH_ADMIN] ?: isBluetoothAdminPermissionGranted
            isBluetoothAdvertisePermissionGranted = permissions[Manifest.permission.BLUETOOTH_ADVERTISE] ?: isBluetoothAdvertisePermissionGranted

            isAccessCoarseLocationPermissionGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: isAccessCoarseLocationPermissionGranted
            isAccessFineLocationPermissionGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: isAccessFineLocationPermissionGranted
        }
        requestPermissions(context)
    }

    fun requestPermissions(context: Context){

        isAccessCoarseLocationPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        isAccessFineLocationPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        isBluetoothScanPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.BLUETOOTH_SCAN
        ) == PackageManager.PERMISSION_GRANTED

        isBluetoothConnectPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED

        isBluetoothPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.BLUETOOTH
        ) == PackageManager.PERMISSION_GRANTED

        isBluetoothAdminPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.BLUETOOTH_ADMIN
        ) == PackageManager.PERMISSION_GRANTED

        isBluetoothAdvertisePermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.BLUETOOTH_ADVERTISE
        ) == PackageManager.PERMISSION_GRANTED

        val permissionRequest :MutableList<String> = ArrayList()

        if (!isBluetoothScanPermissionGranted){
            permissionRequest.add(Manifest.permission.BLUETOOTH_SCAN)
        }
        if (!isBluetoothConnectPermissionGranted){
            permissionRequest.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        if (!isBluetoothPermissionGranted){
            permissionRequest.add(Manifest.permission.BLUETOOTH)
        }

        if (!isBluetoothAdminPermissionGranted){
            permissionRequest.add(Manifest.permission.BLUETOOTH_ADMIN)
        }

        if (!isBluetoothAdvertisePermissionGranted){
            permissionRequest.add(Manifest.permission.BLUETOOTH_ADVERTISE)
        }

        if (!isAccessCoarseLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (!isAccessFineLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }

    }
}