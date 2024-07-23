package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TemplateController {

    @GetMapping("/login")
    fun showLoginForm(): String {
        return "login"
    }

    @GetMapping("/")
    fun showHome(model: Model): String {
        return "index"
    }

}