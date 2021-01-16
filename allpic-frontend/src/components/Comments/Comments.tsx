import { Media, Image, Content } from "rbx";
import { useEffect, useState } from "react";
import { APIMethods } from "../../services/ApiActions";
import { CommentDTO } from "../../types/API";

const Comments = ({ id }: CommentsProps) => {
  const [comments, setComments] = useState<Array<CommentDTO>>([]);

  useEffect(() => {
    const fetchComments = async () => {
      const res = await APIMethods.getComments(id);

      if (res.message?.type === "success" && res.response) {
        setComments(res.response);
      }
    };

    fetchComments();
  }, [id]);

  const parseExactDate = (date: string): string => {
    return new Date(date).toLocaleTimeString(
      'en-gb',
      {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        second: 'numeric'
      }
    );
  }

  return (
    <>
      {comments.map((comment: CommentDTO) => {
        return (
          <Media key={comment.id}>
            <Media.Item as="figure" align="left">
              <Image.Container as="p" size={64}>
                <Image
                  alt="64x64"
                  src="https://bulma.io/images/placeholders/128x128.png"
                />
              </Image.Container>
            </Media.Item>
            <Media.Item align="content">
              <Content>
                <p>
                  <strong>{comment.author}</strong>{" on "}
                  <small>{parseExactDate(comment.timeAdded)}</small>
                  <br />
                  {comment.message}
                </p>
              </Content>
            </Media.Item>
          </Media>
        );
      })}
    </>
  );
};

export interface CommentsProps {
  id: string;
}

export default Comments;
