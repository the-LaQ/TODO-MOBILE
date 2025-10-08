package ru.vinteno.lavka.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import ru.vinteno.lavka.R
import ru.vinteno.lavka.data.Order

class OrderDetailsDialog : BottomSheetDialogFragment() {

    private var order: Order? = null

    companion object {
        private const val ARG_ORDER_ID = "order_id"
        private const val ARG_ORDER_ADDRESS = "order_address"
        private const val ARG_ORDER_ITEMS = "order_items"

        fun newInstance(order: Order): OrderDetailsDialog {
            val dialog = OrderDetailsDialog()
            val args = Bundle()
            args.putInt(ARG_ORDER_ID, order.id)
            args.putString(ARG_ORDER_ADDRESS, order.name)
            args.putString(ARG_ORDER_ITEMS, order.products)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_order_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val address = arguments?.getString(ARG_ORDER_ADDRESS) ?: ""
        val items = arguments?.getString(ARG_ORDER_ITEMS) ?: ""

        val tvDialogAddress = view.findViewById<TextView>(R.id.tvDialogAddress)
        val tvDialogItems = view.findViewById<TextView>(R.id.tvDialogItems)
        val btnAcceptDialog = view.findViewById<MaterialButton>(R.id.btnAcceptDialog)
        val btnClose = view.findViewById<ImageView>(R.id.btnClose)

        tvDialogAddress.text = address

        // Format items as bullet list
        val formattedItems = items.split(",")
            .map { it.trim() }
            .joinToString("\n") { "• $it" }
        tvDialogItems.text = formattedItems

        btnAcceptDialog.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.order_accepted),
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }

        btnClose.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }
}

