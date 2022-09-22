package old.pet.project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_root.submenu
import kotlinx.android.synthetic.main.activity_root.toolbar
import kotlinx.android.synthetic.main.activity_root.tv_text_content
import kotlinx.android.synthetic.main.layout_bottombar.btn_bookmark
import kotlinx.android.synthetic.main.layout_bottombar.btn_like
import kotlinx.android.synthetic.main.layout_bottombar.btn_settings
import kotlinx.android.synthetic.main.layout_bottombar.btn_share
import kotlinx.android.synthetic.main.layout_submenu.btn_text_down
import kotlinx.android.synthetic.main.layout_submenu.btn_text_up
import kotlinx.android.synthetic.main.layout_submenu.switch_mode
import old.pet.project.R
import old.pet.project.extensions.dpToIntPx
import old.pet.project.viewmodels.ArticleState
import old.pet.project.viewmodels.ArticleViewModel
import old.pet.project.viewmodels.BaseViewModel

class RootActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        setupToolbar()
        setupBottombar()
        setupSubmenu()

        val vmFactory = BaseViewModel.ViewModelFactory("0")
        viewModel = ViewModelProviders.of(this, vmFactory).get(ArticleViewModel::class.java)
        viewModel.observeState(this) {
            renderUi(it)
        }
    }

    private fun renderUi(data: ArticleState) {
        btn_settings.isChecked = data.isShowMenu
        if (data.isShowMenu) submenu.open() else submenu.close()

        btn_like.isChecked = data.isLike
        btn_bookmark.isChecked = data.isBookmark

        switch_mode.isChecked = data.isDarkMode
        delegate.localNightMode = if (data.isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

        tv_text_content.textSize = if (data.isBigText) 18f else 14f
        btn_text_up.isChecked = data.isBigText
        btn_text_down.isChecked = !data.isBigText

        tv_text_content.text = if (data.isLoadingContent) "loading content..." else data.content.first() as String

        toolbar.title = data.title ?: "Articles"
        toolbar.subtitle = data.category ?: "loading..."
        if (data.categoryIcon != null) toolbar.logo = AppCompatResources.getDrawable(this, data.categoryIcon as Int)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val logo = if (toolbar.childCount > 2) toolbar.getChildAt(2) as ImageView else null
        logo?.scaleType = ImageView.ScaleType.CENTER_CROP
        val lp = logo?.layoutParams as? Toolbar.LayoutParams
        lp?.let {
            it.width = this.dpToIntPx(40)
            it.height = this.dpToIntPx(40)
            it.marginEnd = this.dpToIntPx(16)
            logo.layoutParams = it
        }
    }

    private fun setupSubmenu() {
        btn_text_up.setOnClickListener { viewModel.handleUpText() }
        btn_text_down.setOnClickListener { viewModel.handleDownText() }
        switch_mode.setOnClickListener { viewModel.handleNightMode() }
    }

    private fun setupBottombar() {
        btn_like.setOnClickListener { viewModel.handleLike() }
        btn_bookmark.setOnClickListener { viewModel.handleBookmark() }
        btn_share.setOnClickListener { viewModel.handleShare() }
        btn_settings.setOnClickListener { viewModel.handleToggleMenu() }
    }

}