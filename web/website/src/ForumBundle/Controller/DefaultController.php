<?php

namespace ForumBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function publicationAction()
    {
        return $this->render('@Forum/dashboard/publication.html.twig');
    }
    public function reclamationAction()
    {
        return $this->render('@Forum/dashboard/reclamation.html.twig');
    }

    public function frontblogAction()
    {
        return $this->render('@Forum/front/blog.html.twig');
    }
    public function frontblogsingleAction()
    {
        return $this->render('@Forum/front/blog-single.html.twig');
    }
}
