import androidx.fragment.app.Fragment

class BaseFragment : Fragment() {
    /**
     * Could handle back press.
     * @return true if back press was handled
     */
    fun onBackPressed(): Boolean {
        return false
    }
}
