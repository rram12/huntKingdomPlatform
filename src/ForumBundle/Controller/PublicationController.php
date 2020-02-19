<?php

namespace ForumBundle\Controller;

use AppBundle\Entity\Post;
use AppBundle\Entity\Postcomment;
use ForumBundle\Entity\Comment;
use ForumBundle\Entity\Publication;
use ForumBundle\Form\PublicationType;
use FOS\UserBundle\Model\UserInterface;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Security\Core\Security;
use Tutorial\BlogBundle\Form\PostType;

class PublicationController extends Controller
{
    public function addPubAction(Request $request)
    {
        $pub= new Publication();
        $form= $this->createForm(PublicationType::class,$pub);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            if ($pub->getImage() !== null){
                $file = $pub->getImage();
                $filename = md5(uniqid()). '.' . $file->guessExtension();
                $file->move($this->getParameter('media_directory'), $filename);
                $pub->setImage($filename);
            }
            else{
                $pub->setImage(' ');
            }
            $pub->setPublishedAt(new \DateTime("now"));
            $pub->setClosed(0);
            $pub->setPinned(0);
            $pub->user = $this->getUser();
            $em = $this->getDoctrine()->getManager();
            $em->persist($pub);
            $em->flush();
            return $this->redirectToRoute("list_publication");
        }
        return $this->render("@Forum/dashboard/addPub.html.twig",array('Form'=>$form->createView()));
    }
    public function addPubFrontAction(Request $request)
    {
        $pub= new Publication();
        $form= $this->createForm(PublicationType::class,$pub);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            if ($pub->getImage() !== null){
            $file = $pub->getImage();
            $filename = md5(uniqid()). '.' . $file->guessExtension();
            $file->move($this->getParameter('media_directory'), $filename);
            $pub->setImage($filename);
            }
            else{
                $pub->setImage(' ');
            }
            $pub->setPublishedAt(new \DateTime("now"));
            $pub->user = $this->getUser();
            $pub->setClosed(0);
            $pub->setPinned(0);
            $em = $this->getDoctrine()->getManager();
            $em->persist($pub);
            $em->flush();
            return $this->redirectToRoute("blog");
        }
        return $this->render("@Forum/front/addPub.html.twig",array('Form'=>$form->createView()));
    }

    public function listPubAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $posts=$em->getRepository('ForumBundle:Publication')->findAll();
        return $this->render('@Forum/dashboard/publication.html.twig', array(
            "posts" =>$posts
        ));
    }
    public function frontBlogAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $posts=$em->getRepository('ForumBundle:Publication')->findAll();
        return $this->render('@Forum/front/blog.html.twig', array(
            "posts" =>$posts
        ));
    }

    public function updatePubAction(Request $request, $id)
    {
        $em=$this->getDoctrine()->getManager();
        $p= $em->getRepository('ForumBundle:Publication')->find($id);
        $form=$this->createForm(PublicationType::class,$p);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            if ($p->getImage() !== null){
                $file = $p->getImage();
                $filename = md5(uniqid()). '.' . $file->guessExtension();
                $file->move($this->getParameter('media_directory'), $filename);
                $p->setImage($filename);
            }
            else{
                $p->setImage(' ');
            }
            $em= $this->getDoctrine()->getManager();
            $em->persist($p);
            $em->flush();
            return $this->redirectToRoute('list_publication');

        }
        return $this->render('@Forum/dashboard/updatePub.html.twig', array(
            "Form"=> $form->createView()
        ));
    }

    public function deletePubAction(Request $request)
    {
        $id = $request->get('id');
        $em= $this->getDoctrine()->getManager();
        $Post=$em->getRepository('ForumBundle:Publication')->find($id);
        $em->remove($Post);
        $em->flush();
        return $this->redirectToRoute('list_publication');
    }
    public function updatePubFrontAction(Request $request, $id)
    {
        $em=$this->getDoctrine()->getManager();
        $p= $em->getRepository('ForumBundle:Publication')->find($id);
        $form=$this->createForm(PublicationType::class,$p);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            if ($p->getImage() !== null){
                $file = $p->getImage();
                $filename = md5(uniqid()). '.' . $file->guessExtension();
                $file->move($this->getParameter('media_directory'), $filename);
                $p->setImage($filename);
            }
            else{
                $p->setImage(' ');
            }
            $em= $this->getDoctrine()->getManager();
            $em->persist($p);
            $em->flush();
            return $this->redirectToRoute('blog');

        }
        return $this->render('@Forum/front/updatePub.html.twig', array(
            "Form"=> $form->createView()
        ));
    }

    public function deletePubFrontAction(Request $request)
    {
        $id = $request->get('id');
        $em= $this->getDoctrine()->getManager();
        $Post=$em->getRepository('ForumBundle:Publication')->find($id);
        $em->remove($Post);
        $em->flush();
        return $this->redirectToRoute('blog');
    }

    public function showPubAction($id)
    {
        $em= $this->getDoctrine()->getManager();
        $p=$em->getRepository('ForumBundle:Publication')->find($id);
        return $this->render('@Forum/front/detailedpost.html.twig', array(
            'post'=>$p
        ));
    }

    public function showPubDashAction($id)
    {
        $em= $this->getDoctrine()->getManager();
        $p=$em->getRepository('ForumBundle:Publication')->find($id);
        return $this->render('@Forum/dashboard/detailedPost.html.twig', array(
            'post'=>$p
        ));
    }

    public function morePubsAction(Request $request,$user)
    {
        $em=$this->getDoctrine()->getManager();
        $pubs=$em->getRepository('ForumBundle:Publication')->findByUser($user);
        return $this->render('@Forum/front/blog.html.twig', array(
            "posts" =>$pubs
        ));
    }

    public function addCommentAction(Request $request, \Symfony\Component\Security\Core\User\UserInterface $user)
    {
        $ref = $request->headers->get('referer');
        $postId = (int) substr($ref,strrpos($ref,"/")+1);
        $post = $this->getDoctrine()->getRepository(Publication::class)->findOneById($postId);
        $comment = new Comment();
        $comment->setUser($user);
        $comment->setPost($post);
        $comment->setContent($request->request->get('comment'));
        $comment->setChecked(0);
        $comment->setPostedAt(new \DateTime("now"));
        $em = $this->getDoctrine()->getManager();
        $em->persist($comment);
        $em->flush();
        return $this->redirect($ref);
    }

    public function deleteCommentAction(Request $request)
    {
        $ref = $request->headers->get('referer');
        $id = $request->get('id');
        $em= $this->getDoctrine()->getManager();
        $comment=$em->getRepository('ForumBundle:Comment')->find($id);
        $em->remove($comment);
        $em->flush();
        return $this->redirect($ref);
    }


}
