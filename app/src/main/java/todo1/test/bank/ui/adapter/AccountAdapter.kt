package todo1.test.bank.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_account.view.*
import todo1.test.bank.R
import todo1.test.bank.model.Account
import todo1.test.bank.util.inflate

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.ViewHolder>(){

    private var list :  List<Account> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflate(R.layout.list_item_account)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(listAccounts: List<Account>){
        list = listAccounts
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(account: Account) = with(itemView) {
            itemView.text_account_type.text = account.type
            itemView.text_account_number.text = account.number
            itemView.text_account_currency.text = account.currency
            itemView.text_account_amount.text = account.balance
        }

    }
}