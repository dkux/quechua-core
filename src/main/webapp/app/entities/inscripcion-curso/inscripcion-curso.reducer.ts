import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInscripcionCurso, defaultValue } from 'app/shared/model/inscripcion-curso.model';

export const ACTION_TYPES = {
  FETCH_INSCRIPCIONCURSO_LIST: 'inscripcionCurso/FETCH_INSCRIPCIONCURSO_LIST',
  FETCH_INSCRIPCIONCURSO: 'inscripcionCurso/FETCH_INSCRIPCIONCURSO',
  CREATE_INSCRIPCIONCURSO: 'inscripcionCurso/CREATE_INSCRIPCIONCURSO',
  UPDATE_INSCRIPCIONCURSO: 'inscripcionCurso/UPDATE_INSCRIPCIONCURSO',
  DELETE_INSCRIPCIONCURSO: 'inscripcionCurso/DELETE_INSCRIPCIONCURSO',
  RESET: 'inscripcionCurso/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInscripcionCurso>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type InscripcionCursoState = Readonly<typeof initialState>;

// Reducer

export default (state: InscripcionCursoState = initialState, action): InscripcionCursoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_INSCRIPCIONCURSO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INSCRIPCIONCURSO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_INSCRIPCIONCURSO):
    case REQUEST(ACTION_TYPES.UPDATE_INSCRIPCIONCURSO):
    case REQUEST(ACTION_TYPES.DELETE_INSCRIPCIONCURSO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_INSCRIPCIONCURSO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INSCRIPCIONCURSO):
    case FAILURE(ACTION_TYPES.CREATE_INSCRIPCIONCURSO):
    case FAILURE(ACTION_TYPES.UPDATE_INSCRIPCIONCURSO):
    case FAILURE(ACTION_TYPES.DELETE_INSCRIPCIONCURSO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSCRIPCIONCURSO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSCRIPCIONCURSO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_INSCRIPCIONCURSO):
    case SUCCESS(ACTION_TYPES.UPDATE_INSCRIPCIONCURSO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_INSCRIPCIONCURSO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/inscripcion-cursos';

// Actions

export const getEntities: ICrudGetAllAction<IInscripcionCurso> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_INSCRIPCIONCURSO_LIST,
  payload: axios.get<IInscripcionCurso>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IInscripcionCurso> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INSCRIPCIONCURSO,
    payload: axios.get<IInscripcionCurso>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IInscripcionCurso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INSCRIPCIONCURSO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInscripcionCurso> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INSCRIPCIONCURSO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInscripcionCurso> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INSCRIPCIONCURSO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
